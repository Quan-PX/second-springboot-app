package com.quanpham.secondApp.Service.InterfaceService;

import com.quanpham.secondApp.dto.request.UserCreationRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailService();

    long saveUser(UserCreationRequest request);

    void updateUser(long userId, UserCreationRequest request);

//    void changeStatus(long userId, UserStatus userStatus);

    void deleteUser(long userId);

//    UserDetailResponse getUser(long userId);

//    PageResponse<?> getAllUsers(int pageNo, int pageSize);

}
