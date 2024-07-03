package com.swd391.bachhoasi.model.dto.response;

import com.swd391.bachhoasi.model.constant.TokenType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipperLoginResponse {
    private String accessToken;
    private String refreshToken;
    private TokenType tokenType;
    public ShipperLoginResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = TokenType.BEARER;
    }
}
