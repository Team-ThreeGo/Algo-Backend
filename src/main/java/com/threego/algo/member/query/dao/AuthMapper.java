package com.threego.algo.member.query.dao;

import com.threego.algo.member.command.domain.aggregate.Role;
import com.threego.algo.member.query.dto.LoginUserResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthMapper {
    LoginUserResponseDTO selectMemberByEmail(
            @Param("email")String email
    );

    List<Role> selectRolesByEmail(String email);
}
