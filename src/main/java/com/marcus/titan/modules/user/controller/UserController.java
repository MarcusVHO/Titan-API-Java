package com.marcus.titan.modules.user.controller;


import com.marcus.titan.modules.user.dto.request.RegisterUserRequest;
import com.marcus.titan.modules.user.dto.response.RegisterUserResponse;
import com.marcus.titan.modules.user.entity.User;
import com.marcus.titan.modules.user.service.CreateUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/com/marcus/titan/modules/user")
public class UserController {
    CreateUserService createUserService;

    public UserController(CreateUserService createUserService) {
        this.createUserService = createUserService;
    }

//    @PostMapping("/register")
//    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
//        User newUser = createUserService.create(request);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterUserResponse(newUser.getName(), newUser.getOneId()));
//    }
}
