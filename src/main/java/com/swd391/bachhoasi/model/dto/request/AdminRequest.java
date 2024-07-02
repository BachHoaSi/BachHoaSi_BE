package com.swd391.bachhoasi.model.dto.request;

import com.swd391.bachhoasi.model.constant.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminRequest {
    private String username;
    private String fullName;
    private Role role;
}
