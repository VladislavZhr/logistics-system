package com.logistics.apigateway.application.service;

import com.logistics.apigateway.application.port.in.AdminQueryUseCase;
import com.logistics.apigateway.application.port.out.LoadBalancesPort;
import com.logistics.apigateway.application.port.out.LoadUsersPort;
import com.logistics.apigateway.domain.model.FullUserInfo;
import com.logistics.apigateway.domain.model.User;
import com.logistics.apigateway.domain.model.UserBalance;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AdminUserAggregationService implements AdminQueryUseCase {

    private final LoadUsersPort loadUsersPort;
    private final LoadBalancesPort loadBalancesPort;

    @Override
    public Mono<List<FullUserInfo>> getAllUserDetailsWithBalance() {
        return Mono.zip(
                loadUsersPort.loadAllUsers(),
                loadBalancesPort.loadAllBalances()
        ).map(tuple -> {
            List<User> users = tuple.getT1();
            List<UserBalance> balances = tuple.getT2();

            return users.stream()
                    .map(user -> {
                        BigDecimal balance = balances.stream()
                                .filter(b -> b.getUserId().equals(user.getId()))
                                .map(UserBalance::getBalance)
                                .findFirst()
                                .orElse(BigDecimal.ZERO);

                        return FullUserInfo.builder()
                                .id(user.getId())
                                .username(user.getUsername())
                                .email(user.getEmail())
                                .role(user.getRole())
                                .balance(balance)
                                .build();
                    })
                    .toList();
        });
    }
}

