package com.quanpham.secondApp.controller;

import com.quanpham.secondApp.service.UserService;
import com.quanpham.secondApp.dto.request.ApiResponse;
import com.quanpham.secondApp.dto.request.UserCreationRequest;
import com.quanpham.secondApp.dto.response.UserResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    //@PreAuthorize("hasRole('ADMIN')")  // voi truong hop role ADMIN
//    @PreAuthorize("hasAuthority('APPROVE_POST')")
    @PostMapping("/registration")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable Long userId){
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .result(userService.getUser(userId))
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    ApiResponse<List<UserResponse>> getUsers(){

        var authentication = SecurityContextHolder.getContext().getAuthentication();    // securitycontextholder chua thong tin user hien tại
        log.info("User: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .result(userService.getUsers())
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable("userId") Long userId, @RequestBody UserCreationRequest request){
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .result(userService.updateUser(userId, request))
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<Void> deleteUser(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("delete user successfully")
                .build();
    }
}
