package com.swd391.bachhoasi.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.swd391.bachhoasi.model.constant.TokenType;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${access-token.secret-key}")
    private String jwtSecret;

    @Value("${access-token.max-age}")
    private long jwtExpiration;

    @Value("${refresh-token.secret-key}")
    private String jwtRefreshSecret;

    @Value("${refresh-token.max-age}")
    private Long jwtRefreshExpiration;

    public String generateToken(Authentication authentication, TokenType tokenType) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return generateToken(user.getUsername(), tokenType);
    }

    public String generateToken(String username, TokenType tokenType) {
        Date currentDate = new Date();
        Date expiryDate = null;
        if(tokenType == TokenType.ACCESS_TOKEN) {
            expiryDate = new Date(currentDate.getTime() + jwtExpiration);
        }else if (tokenType == TokenType.REFRESH_TOKEN) {
            expiryDate = new Date(currentDate.getTime() + jwtRefreshExpiration);
        }
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(tokenType))
                .compact();
    }

    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, TokenType.ACCESS_TOKEN);
    }

    public String generateRefreshToken(Authentication authentication) {
        return generateToken(authentication, TokenType.REFRESH_TOKEN);
    }
    private Key getSigningKey(TokenType tokenType) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokenType == TokenType.ACCESS_TOKEN ? jwtSecret : jwtRefreshSecret));
    }
    public String getUsernameFromJWT(String token, TokenType tokenType) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(tokenType))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean validateToken(String authToken, TokenType tokenType) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey(tokenType)).build().parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
