package com.quanpham.secondApp.Service.JwtService;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails user);

    String extractUser(String token);

    boolean validateToken(String token, UserDetails user);

    String refreshToken(UserDetails user);
}
