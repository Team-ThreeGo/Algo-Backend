package com.threego.algo.security;


import com.threego.algo.auth.command.application.service.AuthService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;


@Slf4j
@Component
public class JwtUtil {

    private final Key key;
    private final AuthService authService;

    public JwtUtil(@Value("${token.secret}") String secret,
                   AuthService authService) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.authService = authService;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("유효하지 않은 토큰");
        } catch (ExpiredJwtException e) {
            log.info("만료된 토큰");
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 토큰");
        } catch (IllegalArgumentException e) {
            log.info("클레임이 비어있는 토큰");
        }

        return false;
    }

    public Authentication getAuthentication(String token) {

        Claims claims = parseClaims(token);
        UserDetails userDetails = authService.loadUserByUsername(claims.getSubject());

        Collection<GrantedAuthority> authorities = null;
        if(claims.get("auth") == null) {
            throw new RuntimeException("토큰에 권한이 없습니다.");
        } else {
            authorities =
                    Arrays.stream(claims.get("auth").toString()
                                    .replace("[", "")
                                    .replace("]", "")
                                    .split(", "))
                            .map(role -> new SimpleGrantedAuthority(role))
                            .collect(Collectors.toList());
        }

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

}
