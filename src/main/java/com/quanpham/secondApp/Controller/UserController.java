package com.quanpham.secondApp.Controller;

import com.quanpham.secondApp.Entity.User;
import com.quanpham.secondApp.Mapper.UserMapper;
import com.quanpham.secondApp.Service.UserService;
import com.quanpham.secondApp.dto.request.UserCreationRequest;
import com.quanpham.secondApp.dto.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    //@PreAuthorize("hasRole('ADMIN')")  // voi truong hop role ADMIN
    @PreAuthorize("hasAuthority('APPROVE_POST')")
    @PostMapping("/users")
    UserResponse createUser(@RequestBody @Valid UserCreationRequest request){
        return userService.createUser(request);
    }
}
