package com.quanpham.secondApp.Service;

import com.quanpham.secondApp.Entity.User;
import com.quanpham.secondApp.Enums.Role;
import com.quanpham.secondApp.Mapper.UserMapper;
import com.quanpham.secondApp.Repository.UserRepository;
import com.quanpham.secondApp.dto.request.UserCreationRequest;
import com.quanpham.secondApp.dto.response.UserResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

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
        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }
}
