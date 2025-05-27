package com.logistics.userservice.infrastructure.adapter.in.controller.dto.passwordDTO;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String email;
    private String oldPassword;
    private String newPassword;
}
