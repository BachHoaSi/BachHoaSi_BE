package com.swd391.bachhoasi.model.dto.response;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDetailResponse{
    private BigDecimal id;
    private String name;
    private String code;
    private String description;
    private UserCategory createBy;
    private Date createdDate;
    private UserCategory updateBy;
    private Date updateDate;

    @Data
    @AllArgsConstructor
    public class UserCategory {
        private BigDecimal id;
        private String name;
        private String role;
    }
}

