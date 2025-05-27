package com.logistics.apigateway.application.port.out;

import com.logistics.apigateway.domain.model.UserBalance;
import reactor.core.publisher.Mono;

import java.util.List;

public interface LoadBalancesPort {
    Mono<List<UserBalance>> loadAllBalances();
}
