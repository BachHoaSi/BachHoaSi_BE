package com.swd391.bachhoasi.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipperLoginDto {
    @NotBlank(message = "email should not blank")
    private String email;
    @NotBlank(message = "Password should not blank")
    private String password;
}
