package com.logistics.userservice.application.port.input;

import com.logistics.userservice.domain.model.Role;
import com.logistics.userservice.infrastructure.adapter.in.controller.dto.authDTO.AuthResponse;

public interface AuthUseCase {
    AuthResponse register(String username, String password, String email, Role role);
    AuthResponse login(String email, String password);
}
