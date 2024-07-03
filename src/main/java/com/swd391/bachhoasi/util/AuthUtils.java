package com.swd391.bachhoasi.util;

import com.swd391.bachhoasi.model.dto.response.ShipperLoginResponse;
import com.swd391.bachhoasi.model.entity.Shipper;
import com.swd391.bachhoasi.repository.ShipperRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.swd391.bachhoasi.model.dto.response.LoginResponse;
import com.swd391.bachhoasi.model.entity.Admin;
import com.swd391.bachhoasi.model.exception.AuthFailedException;
import com.swd391.bachhoasi.repository.AdminRepository;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class AuthUtils {
    private final AdminRepository adminRepository;
    private final ShipperRepository shipperRepository;

    public static HttpHeaders getAuthenticationHeader(LoginResponse loginResponse){
        var header =  new HttpHeaders();
        header.add("Authorization", String.format("%s %s", TextUtils.toCamelCase(loginResponse.toString(), true) , loginResponse.getAccessToken()));
        return header;
    }

    public Admin getAdminUserFromAuthentication() {
        try {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null) throw new AuthFailedException("User is not authenticated, please login");
            String username = auth.getName();
            return adminRepository.findByUsername(username).orElseThrow();
        }catch(Exception ex) {
            throw new AuthFailedException("User is not authenticated, please login");
        }
    }

    public static HttpHeaders shipperGetAuthenticationHeader(ShipperLoginResponse loginResponse){
        var header =  new HttpHeaders();
        header.add("Authorization", String.format("%s %s", TextUtils.toCamelCase(loginResponse.toString(), true) , loginResponse.getAccessToken()));
        return header;
    }

    public Shipper getShipper() {
        try {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null) throw new AuthFailedException("User is not authenticated, please login");
            String username = auth.getName();
            return shipperRepository.findByEmail(username).orElseThrow();
        } catch (Exception ex) {
            throw new AuthFailedException("User is not authenticated, please login");
        }
    }
}
