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
import com.swd391.bachhoasi.model.exception.NotFoundException;
import com.swd391.bachhoasi.model.exception.ValidationFailedException;
import com.swd391.bachhoasi.repository.AdminRepository;
import com.swd391.bachhoasi.service.AdminService;
import com.swd391.bachhoasi.util.AuthUtils;
import com.swd391.bachhoasi.util.BaseUtils;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtils authUtils;

    public PaginationResponse<AdminResponse> getAllAdmin(SearchRequestParamsDto search) {
        var dbResult = adminRepository.searchAnyByParameter(search.search(), search.pagination())
                .map(this::convertToDto);
        return new PaginationResponse<>(dbResult);
    }

    public AdminResponse getAdminById (BigDecimal id) {
        var userDb = adminRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Can't found admin with id: %s", id.toString())));
        return convertToDto(userDb);
    }

    @Override
    public AdminResponse importNewUser(AdminRequest adminRequest) {
        var admin = new Admin();
        admin.setFullName(adminRequest.getFullName());
        admin.setIsActive(true);
        admin.setIsLocked(false);
        String password = BaseUtils.generatePassword(12);
        passwordEncoder.encode(password);
        admin.setHashPassword(password);
        try {
            var dbResult = adminRepository.save(admin);
            return convertToDto(dbResult);
        } catch (Exception ex) {
            throw new ActionFailedException(
                    String.format("Something happen when adding new user to system: %s", ex.getMessage()));
        }
    }

    @Override
    public AdminResponse updateUser(BigDecimal id, AdminRequest adminRequest) {
        var userDb = adminRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Can't found admin with id: %s", id.toString())));
        Admin updateEntity = addUpdateFieldToAdminEntity(adminRequest, userDb);
        try {
            updateEntity = adminRepository.save(updateEntity);
            return convertToDto(updateEntity);
        } catch (Exception ex) {
            throw new ActionFailedException(
                    String.format("Username duplicated or something else error: %s", ex.getMessage()));
        }
    }

    @Override
    public AdminResponse removeUser(BigDecimal id) {
        Admin userDb = adminRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Can't found admin with id: %s", id.toString())));
        var currentUser = authUtils.getAdminUserFromAuthentication();
        if (userDb.getId().equals(id)) {
            throw new ValidationFailedException("Can't disable your self");
        }
        if (currentUser.getRole().compareTo(userDb.getRole()) >= 0) {
            try {
                userDb.setIsActive(false);
                userDb.setIsLocked(true);
                adminRepository.save(userDb);
                return convertToDto(userDb);
            } catch (Exception ex) {
                throw new ActionFailedException(
                    String.format("Something happen when deactivated user: %s", ex.getMessage())
                );
            }
        }
        throw new ValidationFailedException("Can't update user that is higher role");
    }

    @Override
    public AdminResponse changeUserLockStatus(BigDecimal id, Boolean isLock) {
        Admin userDb = adminRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Can't found admin with id: %s", id.toString())));
        var currentUser = authUtils.getAdminUserFromAuthentication();
        if (userDb.getId().equals(id)) {
            throw new ValidationFailedException("Can't change lock status of your self");
        }
        if (currentUser.getRole().compareTo(userDb.getRole()) >= 0) {
            try {
                userDb.setIsActive(isLock);
                adminRepository.save(userDb);
                return convertToDto(userDb);
            } catch (Exception ex) {
                throw new ActionFailedException(
                    String.format("Something happen when change lock status user: %s", ex.getMessage())
                );
            }
        }
        throw new ValidationFailedException("Can't update user that is higher role");
    }

    @Override
    public AdminResponse activeAccount(BigDecimal id) {
        Admin userDb = adminRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Can't found admin with id: %s", id.toString())));
        try {
            userDb.setIsActive(true);
            adminRepository.save(userDb);
            return convertToDto(userDb);
        } catch (Exception ex) {
            throw new ActionFailedException(
                String.format("Something happen when deactivated user: %s", ex.getMessage())
            );
        }
    }

    private AdminResponse convertToDto(Admin item) {
        return AdminResponse.builder()
                .id(item.getId())
                .username(item.getUsername())
                .fullName(item.getFullName())
                .isActive(item.getIsActive())
                .isLocked(item.getIsLocked())
                .role(item.getRole().toString())
                .build();
    }

    private Admin addUpdateFieldToAdminEntity(AdminRequest request, Admin adminEntity) {
        if (adminEntity == null || request == null) {
            throw new ValidationFailedException(
                    "Request or Admin entity is null on function::addUpdateFieldToAdminEntity");
        }
        adminEntity.setUsername(request.getUsername());
        adminEntity.setFullName(request.getFullName());
        adminEntity.setRole(request.getRole());
        return adminEntity;
    }
}
