package com.quanpham.secondApp.Controller;

import com.quanpham.secondApp.Entity.User;
import com.quanpham.secondApp.Service.UserService;
import com.quanpham.secondApp.dto.request.UserCreationRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    User createUser(@RequestBody @Valid UserCreationRequest request){
        return userService.createUser(request);
    }
}
