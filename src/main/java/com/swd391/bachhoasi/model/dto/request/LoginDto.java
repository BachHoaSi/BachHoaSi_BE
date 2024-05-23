package com.swd391.bachhoasi.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @NotBlank(message = "Username should not blank")
    private String username;
    @NotBlank(message = "Password should not blank")
    private String password;
}
