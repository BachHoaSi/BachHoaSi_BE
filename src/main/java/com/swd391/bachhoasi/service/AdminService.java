package com.swd391.bachhoasi.service;

import java.math.BigDecimal;

import com.swd391.bachhoasi.model.dto.request.AdminRequest;
import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.AdminResponse;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;

public interface AdminService {
    PaginationResponse<AdminResponse> getAllAdmin(SearchRequestParamsDto search);
    AdminResponse importNewUser(AdminRequest adminRequest);
    AdminResponse updateUser(BigDecimal id ,AdminRequest adminRequest);
    AdminResponse removeUser(BigDecimal id);
    AdminResponse changeUserLockStatus(BigDecimal id, Boolean isLock);

    AdminRequest activeAccount(BigDecimal id);

}
