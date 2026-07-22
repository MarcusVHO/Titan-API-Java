package com.marcus.titan.infra.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.marcus.titan.modules.user.entity.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Component
public class TokenConfig {
    private String secret="banana";

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withClaim("id", user.getId())
                    .withSubject(String.valueOf(user.getOneId()))
                    .withExpiresAt(genExpirationDate())
                    .withIssuedAt(Instant.now())
                    .sign(algorithm);

        } catch (JWTCreationException exception) {
            throw  new RuntimeException("Error while generation token", exception);
        }
    }

    public Optional<JWTUserData> validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decode = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return Optional.of(JWTUserData.builder()
                    .userId(decode.getClaim("id").asInt())
                    .name(decode.getClaim("name").asString())
                    .build()
            );
        }
        catch (JWTVerificationException ex) {
            return Optional.empty();
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now()
                .plusHours(2)
                .atZone(ZoneId.of("America/Sao_Paulo"))
                .toInstant();
    }
}
