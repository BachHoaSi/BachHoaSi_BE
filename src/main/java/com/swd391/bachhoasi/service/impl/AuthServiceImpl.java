package com.swd391.bachhoasi.service.impl;

import com.swd391.bachhoasi.model.constant.Role;
import com.swd391.bachhoasi.model.dto.request.LoginDto;
import com.swd391.bachhoasi.model.dto.response.LoginResponse;
import com.swd391.bachhoasi.model.entity.Admin;
import com.swd391.bachhoasi.model.exception.AuthFailedException;
import com.swd391.bachhoasi.model.exception.UserNotFoundException;
import com.swd391.bachhoasi.repository.AdminRepository;
import com.swd391.bachhoasi.security.JwtProvider;
import com.swd391.bachhoasi.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AdminRepository adminRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override
    public LoginResponse login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = jwtProvider.generateToken(authentication);
            Admin user = adminRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
            .orElseThrow();
            return new LoginResponse(accessToken, "", user.getRole());
        }catch (Exception e){
            throw new AuthFailedException("Username or password is not correct, please check again");
        }
    }
}
