package com.marcus.titan.modules.user.dto.response;

public record UserResponse(
        Integer id,
        Long oneId,
        String name,
        String role,
        boolean active
) {
}
