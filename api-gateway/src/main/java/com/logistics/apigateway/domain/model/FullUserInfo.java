package com.logistics.apigateway.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FullUserInfo {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private BigDecimal balance;
}

