package com.quanpham.secondApp.service.interfaceService;

import com.quanpham.secondApp.dto.request.UserCreationRequest;
import com.quanpham.secondApp.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;

public interface IUserService {

    UserDetailsService userDetailService();

    UserResponse createUser(UserCreationRequest request);

    UserResponse updateUser(long userId, UserCreationRequest request);

    UserResponse getUser(long userId);

    List<UserResponse> getUsers();


//    void changeStatus(long userId, UserStatus userStatus);

    void deleteUser(long userId);

//    PageResponse<?> getAllUsers(int pageNo, int pageSize);

}
