package com.logistics.userservice.infrastructure.jwt;

import com.logistics.userservice.application.port.output.JwtTokenPort;
import com.logistics.userservice.domain.model.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtService implements JwtTokenPort {

    private final JwtProperties jwtProperties;

    @Override
    public String generateToken(Long userId, String email, Role role) {
        return Jwts.builder()
                .setSubject(userId.toString())                     // тут головний ідентифікатор
                .claim("email", email)
                .claim("role", role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }
}
