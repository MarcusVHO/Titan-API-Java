package com.marcus.titan.modules.user.service;

import com.marcus.titan.modules.user.dto.request.RegisterUserRequest;
import com.marcus.titan.modules.user.entity.User;
import com.marcus.titan.modules.user.enums.UserRole;
import com.marcus.titan.modules.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public CreateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public User create(RegisterUserRequest request) {
//        User newUser = new User();
//        newUser.setPassword(passwordEncoder.encode(request.password()));
//        newUser.setRegistration(request.registration());
//        newUser.setName(request.name());
//        newUser.setRole(UserRole.ADMIN);
//
//        userRepository.save(newUser);
//        return newUser;
//    }
}
