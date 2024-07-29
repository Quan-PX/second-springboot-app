package com.quanpham.secondApp.service;


import com.quanpham.secondApp.exception.AppException;
import com.quanpham.secondApp.exception.ErrorCode;
import com.quanpham.secondApp.repository.UserRepository;
import com.quanpham.secondApp.service.interfaceService.JwtUntil;
import com.quanpham.secondApp.dto.request.AuthenticationRequest;
import com.quanpham.secondApp.dto.request.IntrospectTokenRequest;
import com.quanpham.secondApp.dto.response.AuthenticationResponse;
import com.quanpham.secondApp.dto.response.IntrospectTokenResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationHandleService {

    UserRepository userRepository;
    AuthenticationManager authenticationManager;
    JwtUntil jwtUntil;

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        String accessToken = jwtUntil.generateToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .authenticated(true)
                .refreshToken("")
                .userId(user.getId())
                .build();
    }

    public IntrospectTokenResponse introspect(IntrospectTokenRequest request){
        var token = request.getToken();
        boolean isValid = true;

        try{
            jwtUntil.validateToken(token);
        } catch(AppException e){
            isValid = false;
        }

        return IntrospectTokenResponse.builder()
                .valid(isValid)
                .build();
    }


}
