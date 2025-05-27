package com.logistics.apigateway.application.port.out;

import com.logistics.apigateway.domain.model.User;
import reactor.core.publisher.Mono;

import java.util.List;

public interface LoadUsersPort {
    Mono<List<User>> loadAllUsers();
}
