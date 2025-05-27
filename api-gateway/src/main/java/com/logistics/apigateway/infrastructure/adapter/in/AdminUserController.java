package com.logistics.apigateway.infrastructure.adapter.in;

import com.logistics.apigateway.application.port.in.AdminQueryUseCase;
import com.logistics.apigateway.domain.model.FullUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/gateway-admin")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminQueryUseCase adminQueryUseCase;

    @GetMapping("/users-with-balance")
    public Mono<ResponseEntity<List<FullUserInfo>>> getAllUserDetailsWithBalance() {
        return adminQueryUseCase.getAllUserDetailsWithBalance()
                .map(ResponseEntity::ok);
    }

}