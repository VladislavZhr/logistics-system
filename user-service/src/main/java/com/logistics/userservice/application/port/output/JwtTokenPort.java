package com.logistics.userservice.application.port.output;

import com.logistics.userservice.domain.model.Role;

public interface JwtTokenPort {
    String generateToken(Long userId, String email, Role role);
}
