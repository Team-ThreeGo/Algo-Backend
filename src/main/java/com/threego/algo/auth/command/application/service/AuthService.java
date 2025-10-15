package com.threego.algo.auth.command.application.service;

import com.threego.algo.auth.command.application.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    void registUser(UserDTO newUser);
    Integer getMemberIdByEmail(String email);
}
