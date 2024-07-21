package com.quanpham.secondApp.Service;

import com.quanpham.secondApp.Entity.User;
import com.quanpham.secondApp.Enums.Role;
import com.quanpham.secondApp.Exception.AppException;
import com.quanpham.secondApp.Exception.ErrorCode;
import com.quanpham.secondApp.Mapper.UserMapper;
import com.quanpham.secondApp.Repository.UserRepository;
import com.quanpham.secondApp.dto.request.UserCreationRequest;
import com.quanpham.secondApp.dto.response.UserResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements com.quanpham.secondApp.Service.InterfaceService.UserService {

    final UserRepository userRepository;
    final UserMapper userMapper;
    final PasswordEncoder passwordEncoder;

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
        roles.add(Role.USER.name());
//        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserDetailsService userDetailService() {
        return username -> userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
    }

    @Override
    public long saveUser(UserCreationRequest request) {
        return 0;
    }

    @Override
    public void updateUser(long userId, UserCreationRequest request) {

    }

    @Override
    public void deleteUser(long userId) {

    }
}
