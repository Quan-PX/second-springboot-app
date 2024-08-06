package com.quanpham.secondApp.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.quanpham.secondApp.entity.InvalidatedToken;
import com.quanpham.secondApp.entity.User;
import com.quanpham.secondApp.exception.AppException;
import com.quanpham.secondApp.exception.ErrorCode;
import com.quanpham.secondApp.repository.InvalidatedRepository;
import com.quanpham.secondApp.repository.UserRepository;
import com.quanpham.secondApp.dto.request.AuthenticationRequest;
import com.quanpham.secondApp.dto.request.IntrospectTokenRequest;
import com.quanpham.secondApp.dto.request.LogoutRequest;
import com.quanpham.secondApp.dto.response.AuthenticationResponse;
import com.quanpham.secondApp.dto.response.IntrospectTokenResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

//@Service
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationService {

    UserRepository userRepository;
    InvalidatedRepository invalidatedRepository;

    @NonFinal
    @Value("${jwt.secretKey}")
    protected String SIGNER_KEY;


    public IntrospectTokenResponse introspectTokenResponse(IntrospectTokenRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;
        try {
            verifyToken(token);
        } catch (AppException e) {
            isValid = false;
        }
        return IntrospectTokenResponse.builder()
                .valid(isValid)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request){
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        var token = generateToken(user);

        if(!authenticated){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return AuthenticationResponse.builder()
                .accessToken(token)
                .authenticated(true)
                .build();
    }

    private String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("quanpham")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())        // set id cho token logout
                .claim("customClaim-createAppByQuanPham", "6996")
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
//            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }

    }

    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");

        if(!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName()); //add role vao list voi field ROLE_
                if(CollectionUtils.isEmpty(role.getPermissions())) {
//                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));    // add permission nhung ko can field
                }
            });
        return stringJoiner.toString();
    };

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expityTimeToken = signedJWT.getJWTClaimsSet().getExpirationTime(); // kiem tra token het han chua

        var verified = signedJWT.verify(jwsVerifier);
        if(!(verified && expityTimeToken.after(new Date()))){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if(invalidatedRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        var signToken = verifyToken(request.getToken());

        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();
        invalidatedRepository.save(invalidatedToken);
    }

}
