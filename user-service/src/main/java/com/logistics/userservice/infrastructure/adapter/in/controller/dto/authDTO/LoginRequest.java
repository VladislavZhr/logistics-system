package com.logistics.userservice.infrastructure.adapter.in.controller.dto.authDTO;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
