package com.swd391.bachhoasi.service;

import com.swd391.bachhoasi.model.dto.request.LoginDto;
import com.swd391.bachhoasi.model.dto.request.ShipperLoginDto;
import com.swd391.bachhoasi.model.dto.response.LoginResponse;
import com.swd391.bachhoasi.model.dto.response.ShipperLoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    LoginResponse login(LoginDto loginDto);
    LoginResponse createAccessToken(String refreshToken);
    ShipperLoginResponse shipperLogin(ShipperLoginDto shipperLoginDto);

}
