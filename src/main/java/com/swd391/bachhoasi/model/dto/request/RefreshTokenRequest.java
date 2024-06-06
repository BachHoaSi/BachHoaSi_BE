package com.swd391.bachhoasi.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    @NotBlank(message = "Refresh token")
    private String refreshToken;
}
