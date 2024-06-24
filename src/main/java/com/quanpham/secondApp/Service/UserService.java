package com.quanpham.secondApp.Service;

import com.quanpham.secondApp.Entity.User;
import com.quanpham.secondApp.Mapper.UserMapper;
import com.quanpham.secondApp.Repository.UserRepository;
import com.quanpham.secondApp.dto.request.UserCreationRequest;
import com.quanpham.secondApp.dto.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    public UserResponse createUser(UserCreationRequest request){
//        User user = new User();
//        user.setUsername(request.getUsername());
//        user.setFirstName(request.getFirstName());
//        user.setPassword(request.getPassword());
//        user.setLastName(request.getLastName());
//        user.setDob(request.getDob());            // thay vi phai viet ntn, ==> ta se viet nhu duoi khi su dung mapper

        User user = userMapper.toUser(request);

        return userMapper.toUserResponse(userRepository.save(user));
    }
}
