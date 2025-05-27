package com.logistics.paymentservice.application.port.out.details;

import com.logistics.paymentservice.domain.model.UserBalance;

import java.util.List;
import java.util.Optional;

public interface UserBalancePort {
    Optional<UserBalance> findBalanceByUserId(String userId);
    void saveUserBalance(UserBalance userBalance);
    List<UserBalance> findAllUserBalance();
}
