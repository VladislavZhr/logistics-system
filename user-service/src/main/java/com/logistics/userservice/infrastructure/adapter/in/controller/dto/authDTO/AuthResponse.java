package com.logistics.userservice.infrastructure.adapter.in.controller.dto.authDTO;

import com.logistics.userservice.domain.model.Role;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AuthResponse {
    private String username;
    private String email;
    private Role role;
    private String token;
}
