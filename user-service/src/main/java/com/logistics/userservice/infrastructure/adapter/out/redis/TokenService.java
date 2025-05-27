package com.logistics.userservice.infrastructure.adapter.out.redis;

import com.logistics.userservice.application.port.output.ForgetTokenPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService implements ForgetTokenPort {

    private final StringRedisTemplate redisTemplate;

    private static final long EXPIRATION_MINUTES = 5;

    public String generateToken(String email) {
        String token = UUID.randomUUID().toString();
        String hashed = TokenHasher.hash(token);
        redisTemplate.opsForValue().set(hashed, email, EXPIRATION_MINUTES, TimeUnit.MINUTES);
        return token;
    }

    public String getEmailByToken(String token) {
        String hashed = TokenHasher.hash(token);
        return redisTemplate.opsForValue().get(hashed);
    }

    public void deleteToken(String token) {
        String hashed = TokenHasher.hash(token);
        redisTemplate.delete(hashed);
    }
}
