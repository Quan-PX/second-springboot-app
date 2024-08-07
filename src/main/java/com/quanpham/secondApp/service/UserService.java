package com.quanpham.secondApp.service;

import com.quanpham.secondApp.entity.Role;
import com.quanpham.secondApp.entity.User;
import com.quanpham.secondApp.enums.Gender;
import com.quanpham.secondApp.enums.RoleEnum;
import com.quanpham.secondApp.enums.UserStatus;
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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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

        request.setGender(Gender.valueOf(request.getGender().toUpperCase()).toString());

        HashSet<Role> roles = new HashSet<>();
        var userRole = roleRepository.findByName(RoleEnum.USER.name()).orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));
        roles.add(userRole);

        User user = userMapper.toUser(request);
        user.setStatus(UserStatus.ACTIVE);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

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
        request.setGender(Gender.valueOf(request.getGender().toUpperCase()).toString());
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        var roles = roleRepository.findByName(user.getRoles());
//        user.setRoles(new HashSet<>(roles));
        user.getDateOfBirth();
        return userMapper.toUserResponse(userRepository.save(user));
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
