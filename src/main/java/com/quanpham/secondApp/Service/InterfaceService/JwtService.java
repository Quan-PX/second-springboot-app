package com.quanpham.secondApp.Service.InterfaceService;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails user);

    String extractUser(String token);

    boolean validateToken(String token);

    String refreshToken(UserDetails user);
}
