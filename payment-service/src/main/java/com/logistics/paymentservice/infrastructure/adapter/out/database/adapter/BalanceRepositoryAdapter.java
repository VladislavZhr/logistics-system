package com.logistics.paymentservice.infrastructure.adapter.out.database.adapter;

import com.logistics.paymentservice.application.port.out.details.UserBalancePort;
import com.logistics.paymentservice.domain.model.UserBalance;
import com.logistics.paymentservice.infrastructure.adapter.out.database.mapper.BalanceMapper;
import com.logistics.paymentservice.infrastructure.adapter.out.database.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class BalanceRepositoryAdapter implements UserBalancePort {

    private final BalanceRepository repository;

    @Override
    public List<UserBalance> findAllUserBalance(){
        return repository.findAll().stream()
                .map(BalanceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<UserBalance> findBalanceByUserId(String userId){
        return repository.findByUserId(userId).map(BalanceMapper::toDomain);
    }

    @Override
    public void saveUserBalance(UserBalance userBalance) {
        repository.save(BalanceMapper.toEntity(userBalance));
    }

}
