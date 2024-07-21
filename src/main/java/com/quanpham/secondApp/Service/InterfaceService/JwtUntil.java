package com.quanpham.secondApp.Service.InterfaceService;

import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtUntil implements JwtService {

    @Value("${jwt.timeOut}")
    private long expiryTime;

    @Value("${jwt.expiryDay}")
    private long expiryDay;

    @Value("${jwt.signerKey}")
    private String secretKey;

    @Override
    public String generateToken(UserDetails user) {
        return generateToken(new HashMap<>(), user);
    }

    @Override
    public String extractUser(String token) {
        return extracClaim(token, Claims::getSubject);
    }

    @Override
    public boolean validateToken(String token) {
        final String username = extractUser(token); // truyen token vao va extract
        try {
            Claims claims = extraAllClaim(token);
            // Kiểm tra thời hạn của token
            Date expirationDate = claims.getExpiration();
            if (expirationDate.before(new Date()) && username!=null) {
                return false; // Token đã hết hạn
            }
            // Kiểm tra tính hợp lệ của token ở đây (nếu cần)
            return true; // Token hợp lệ
        } catch (Exception e) {
            return false; // Token không hợp lệ
        }
    }

    @Override
    public String refreshToken(UserDetails user) {
        return generateRefreshToken(new HashMap<>(), user);
    }

    public String generateToken(Map<String, Objects> claims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*expiryTime))
                .signWith(getKey(), SignatureAlgorithm.ES256)
                .compact();
    }

    // dung de refresh Token
    public String generateRefreshToken(Map<String, Objects> claims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24*expiryDay))  // 14 ngay - 2 tuan
                .signWith(getKey(), SignatureAlgorithm.ES256)
                .compact();
    }

    // goi 1 lan de generate secret key vi base 64 yeu cau ma hoa
    private String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32]; // 256-bit key
        random.nextBytes(keyBytes);
        String secretKey = Encoders.BASE64.encode(keyBytes);
        return secretKey;
    }

    private Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private <T> T extracClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extraAllClaim(token);
        return claimsResolver.apply(claims);
    }

    private Claims extraAllClaim(String token){
        return Jwts.parser().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }
}
