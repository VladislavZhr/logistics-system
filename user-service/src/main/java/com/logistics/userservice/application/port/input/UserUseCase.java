package com.logistics.userservice.application.port.input;

import com.logistics.userservice.infrastructure.adapter.in.controller.dto.authDTO.AuthResponse;

public interface UserUseCase {
    AuthResponse getCurrentUser(String email);

}
