package com.marcus.titan.modules.user.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RegisterUserRequest (
        @NotEmpty(message = "name is necessary!") String name,
        @NotNull(message = "registration is necessary!") Long one,
        @NotEmpty(message = "password is necessary!") String password,
        @NotEmpty(message = "role is necessary!") String role
    ){
}
