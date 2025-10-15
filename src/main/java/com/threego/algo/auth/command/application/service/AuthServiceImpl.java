package com.threego.algo.auth.command.application.service;
import com.threego.algo.auth.command.application.dto.UserDTO;
import com.threego.algo.member.command.domain.aggregate.Member;
import com.threego.algo.member.command.domain.aggregate.MemberRank;
import com.threego.algo.member.command.domain.aggregate.MemberRole;
import com.threego.algo.member.command.domain.aggregate.Role;
import com.threego.algo.member.command.domain.repository.MemberRankRepository;
import com.threego.algo.member.command.domain.repository.MemberRepository;
import com.threego.algo.member.query.dao.AuthMapper;
import com.threego.algo.member.query.dto.LoginUserResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.threego.algo.member.command.domain.aggregate.Member.UserToMember;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    ModelMapper modelMapper;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    MemberRepository memberRepository;
    MemberRankRepository memberRankRepository;
    AuthMapper authMapper;

    @Autowired
    public AuthServiceImpl(MemberRepository memberRepository,
                           MemberRankRepository memberRankRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           ModelMapper modelMapper,
                           AuthMapper authMapper
    ) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
        this.authMapper = authMapper;
        this.memberRankRepository = memberRankRepository;
    }

    @Override
    public void registUser(UserDTO userDTO) {
        MemberRank defaultRank = memberRankRepository.findById(1)
                .orElseThrow(() -> new IllegalArgumentException("기본 Rank 없음"));

        Member member = UserToMember(userDTO, defaultRank);
        member.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        Member savedMember = memberRepository.save(member);

        // ROLE_USER 기본 권한 부여
        Role userRole = new Role();
        userRole.setId(1);
        MemberRole memberRole = new MemberRole(savedMember, userRole);

        savedMember.getMemberRoles().add(memberRole);
        memberRepository.save(savedMember);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LoginUserResponseDTO loginUser = authMapper.selectMemberByEmail(email);
        if (loginUser == null) {
            throw new UsernameNotFoundException(email + "이메일 사용자는 존재하지 않습니다.");
        }

        List<Role> roles = authMapper.selectRolesByEmail(email);
        List<GrantedAuthority> grantedAuthorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                .map(authority -> (GrantedAuthority) authority)
                .collect(Collectors.toList());

//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(loginUser.getEmail(), loginUser.getPassword(), true, true, true, true, grantedAuthorities);
    }

    @Override
    public Integer getMemberIdByEmail(String email) {
        LoginUserResponseDTO loginUser = authMapper.selectMemberByEmail(email);
        if (loginUser == null) {
            return null;
        }
        return loginUser.getId();
    }
}
