package com.logistics.userservice.infrastructure.adapter.in.controller.dto.passwordDTO;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String token;
    private String newPassword;
}
