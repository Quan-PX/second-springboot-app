package com.quanpham.secondApp.Controller;

import com.nimbusds.jose.JOSEException;
import com.quanpham.secondApp.Service.AuthenticationService;
import com.quanpham.secondApp.dto.request.ApiResponse;
import com.quanpham.secondApp.dto.request.AuthenticationRequest;
import com.quanpham.secondApp.dto.request.IntrospectTokenRequest;
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

    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
           var result = authenticationService.authenticate(request);
           return ApiResponse.<AuthenticationResponse>builder()
                   .result(result)
                   .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectTokenResponse> authenticate(@RequestBody IntrospectTokenRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introspectTokenResponse(request);
        return ApiResponse.<IntrospectTokenResponse>builder()
                .result(result)
                .build();
    }
}
