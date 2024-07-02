package com.swd391.bachhoasi.service.impl;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.swd391.bachhoasi.model.dto.request.AdminRequest;
import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.AdminResponse;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.entity.Admin;
import com.swd391.bachhoasi.model.exception.ActionFailedException;
import com.swd391.bachhoasi.repository.AdminRepository;
import com.swd391.bachhoasi.service.AdminService;
import com.swd391.bachhoasi.util.BaseUtils;


@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    public PaginationResponse<AdminResponse> getAllAdmin(SearchRequestParamsDto search) {
        var dbResult = adminRepository.searchAnyByParameter(search.search(), search.pagination()).map(item -> 
        AdminResponse.builder()
        .id(item.getId())
        .fullName(item.getFullName())
        .isActive(item.getIsActive())
        .isLocked(item.getIsLocked())
        .role(item.getRole().toString())
        .build()
        );
        return new PaginationResponse<>(dbResult);
    }
    @Override
    public AdminResponse importNewUser(AdminRequest adminRequest) {
        var admin = new Admin();
        admin.setFullName(adminRequest.getFullName());
        admin.setIsActive(false);
        admin.setIsLocked(false);
        String password = BaseUtils.generatePassword(12);
        passwordEncoder.encode(password);
        admin.setHashPassword(password);
        try {
            var dbResult = adminRepository.save(admin);
            return convertToDto(dbResult);
        } catch (Exception ex) {
            throw new ActionFailedException("");
        }
    }
    @Override
    public AdminResponse updateUser(BigDecimal id, AdminRequest adminRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }
    @Override
    public AdminResponse removeUser(BigDecimal id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeUser'");
    }
    @Override
    public AdminResponse changeUserLockStatus(BigDecimal id, Boolean isLock) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changeUserLockStatus'");
    }
    @Override
    public AdminRequest activeAccount(BigDecimal id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'activeAccount'");
    }

    private AdminResponse convertToDto(Admin item) {
        return AdminResponse.builder()
        .id(item.getId())
        .fullName(item.getFullName())
        .isActive(item.getIsActive())
        .isLocked(item.getIsLocked())
        .role(item.getRole().toString())
        .build();
    }
}
