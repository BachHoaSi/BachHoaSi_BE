package com.swd391.bachhoasi.service.impl;

import com.swd391.bachhoasi.model.entity.Admin;
import com.swd391.bachhoasi.model.exception.AuthFailedException;
import com.swd391.bachhoasi.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Admin admin = adminRepository.findByUsername(username).orElseThrow(() -> new AuthFailedException("Wrong username, please check again !!!"));
        return new User(admin.getUsername(), admin.getHashPassword(), rolesToAuthority(admin));
    }

    private Collection<GrantedAuthority> rolesToAuthority(Admin user) {
        return Collections.singletonList(
            new SimpleGrantedAuthority(String.format("ROLE_%s", user.getRole().name()))
        );
    }
}
