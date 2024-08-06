package com.quanpham.secondApp.controller;

import com.nimbusds.jose.JOSEException;
import com.quanpham.secondApp.repository.UserRepository;
import com.quanpham.secondApp.service.AuthenticationHandleService;
import com.quanpham.secondApp.dto.request.*;
import com.quanpham.secondApp.dto.response.AuthenticationResponse;
import com.quanpham.secondApp.dto.response.IntrospectTokenResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

//    AuthenticationService authenticationService;
    AuthenticationHandleService authenticationHandleService;
    UserRepository userRepository;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
           var result = authenticationHandleService.authenticate(request);
           var user = userRepository.findByUsername(request.getUsername());

           return ApiResponse.<AuthenticationResponse>builder()
                   .result(result)
                   .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectTokenResponse> authenticate(@RequestBody IntrospectTokenRequest request) throws ParseException, JOSEException {
        var result = authenticationHandleService.introspect(request);
        return ApiResponse.<IntrospectTokenResponse>builder()
                .result(result)
                .build();
    }

//    @PostMapping("/refresh")
//    ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request)
//            throws ParseException, JOSEException {
//        var result = authenticationService.refreshToken(request);
//        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
//    }
//
//    @PostMapping("/logout")
//    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
//        authenticationService.logout(request);
//        return ApiResponse.<Void>builder()
//                .build();
//    }
}
