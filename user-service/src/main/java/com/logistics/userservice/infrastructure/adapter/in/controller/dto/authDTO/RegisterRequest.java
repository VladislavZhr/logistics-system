package com.logistics.userservice.infrastructure.adapter.in.controller.dto.authDTO;

import com.logistics.userservice.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private Role role;

}
