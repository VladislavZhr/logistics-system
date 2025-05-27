package com.logistics.paymentservice.infrastructure.adapter.out.database.adapter;

import com.logistics.paymentservice.application.port.out.fiat.FiatTransactionPort;
import com.logistics.paymentservice.domain.model.CryptoTransaction;
import com.logistics.paymentservice.domain.model.FiatTransaction;
import com.logistics.paymentservice.infrastructure.adapter.out.database.mapper.CryptoMapper;
import com.logistics.paymentservice.infrastructure.adapter.out.database.mapper.FiatMapper;
import com.logistics.paymentservice.infrastructure.adapter.out.database.repository.FiatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FiatRepositoryAdapter implements FiatTransactionPort {

    private final FiatRepository fiatRepository;

    @Override
    public void save(FiatTransaction fiatTransaction) {
        fiatRepository.save(FiatMapper.toEntity(fiatTransaction));
    }

    @Override
    public Optional<FiatTransaction> findByInvoiceId(String invoiceId){
        return fiatRepository.findByInvoiceId(invoiceId).map(FiatMapper::toDomain);
    }

    @Override
    public List<FiatTransaction> findTransactionByUserId(String userId){
        return fiatRepository.findByUserId(userId).stream()
                .map(FiatMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<FiatTransaction> getAllFiatTransactions(){
        return fiatRepository.findAll().stream()
                .map(FiatMapper::toDomain)
                .collect(Collectors.toList());
    }
}
