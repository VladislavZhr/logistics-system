package com.logistics.apigateway.application.port.in;

import com.logistics.apigateway.domain.model.FullUserInfo;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AdminQueryUseCase {
    Mono<List<FullUserInfo>> getAllUserDetailsWithBalance();
}
