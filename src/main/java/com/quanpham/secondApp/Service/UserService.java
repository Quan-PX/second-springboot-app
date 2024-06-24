package com.quanpham.secondApp.Service;

import com.quanpham.secondApp.Entity.User;
import com.quanpham.secondApp.Repository.UserRepository;
import com.quanpham.secondApp.dto.request.UserCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserCreationRequest request){
        User user = new User();

        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setPassword(request.getPassword());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());

        return userRepository.save(user);
    }
}
