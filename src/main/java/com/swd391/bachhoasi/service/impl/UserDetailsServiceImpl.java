package com.swd391.bachhoasi.service.impl;

import com.swd391.bachhoasi.model.entity.Admin;
import com.swd391.bachhoasi.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Admin admin = adminRepository.findByUsername(username);
        GrantedAuthority authority = new SimpleGrantedAuthority(admin.getRole().name());
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(authority);
        return new User(admin.getUsername(), admin.getHashPassword(), authorities);
    }
}
