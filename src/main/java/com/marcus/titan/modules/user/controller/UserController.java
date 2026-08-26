package com.marcus.titan.modules.user.controller;


import com.marcus.titan.modules.user.service.CreateUserService;
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
