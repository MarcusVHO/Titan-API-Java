package com.marcus.titan.infra.security.config;


import lombok.Builder;

@Builder
public record JWTUserData(Integer userId, String name) {
}
