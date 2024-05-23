package com.swd391.bachhoasi.service.impl;

import com.swd391.bachhoasi.model.dto.request.LoginDto;
import com.swd391.bachhoasi.model.dto.response.LoginResponse;
import com.swd391.bachhoasi.repository.AdminRepository;
import com.swd391.bachhoasi.security.JwtProvider;
import com.swd391.bachhoasi.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final AdminRepository adminRepository;
    private final JwtProvider jwtProvider;
    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, AdminRepository adminRepository, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.adminRepository = adminRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public LoginResponse login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new LoginResponse(jwtProvider.generateToken(authentication));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
