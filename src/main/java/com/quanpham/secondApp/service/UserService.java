package com.quanpham.secondApp.service;

import com.quanpham.secondApp.entity.User;
import com.quanpham.secondApp.enums.RoleEnum;
import com.quanpham.secondApp.exception.AppException;
import com.quanpham.secondApp.exception.ErrorCode;
import com.quanpham.secondApp.mapper.UserMapper;
import com.quanpham.secondApp.repository.RoleRepository;
import com.quanpham.secondApp.repository.UserRepository;
import com.quanpham.secondApp.service.interfaceService.IUserService;
import com.quanpham.secondApp.dto.request.UserCreationRequest;
import com.quanpham.secondApp.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest request){
//        User user = new User();
//        user.setUsername(request.getUsername());
//        user.setFirstName(request.getFirstName());
//        user.setPassword(request.getPassword());
//        user.setLastName(request.getLastName());
//        user.setDob(request.getDob());            // thay vi phai viet ntn, ==> ta se viet nhu duoi khi su dung mapper

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(RoleEnum.USER.name());

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getUsers(){
        log.info("In method get Users");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserDetailsService userDetailService() {
        return username -> userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
    }

    @Override
    public UserResponse updateUser(long userId, UserCreationRequest request) {
        User user = userRepository.findById(userId).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

//        var roles = roleRepository.findById(request.getRole)
//        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getUser(long userId) {
        log.info("In method get User By Id");
        return userMapper.toUserResponse(userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public void deleteUser(long userId) {
        log.info("In method delete UserID" + userId);
        userRepository.deleteById(userId);
    }
}
