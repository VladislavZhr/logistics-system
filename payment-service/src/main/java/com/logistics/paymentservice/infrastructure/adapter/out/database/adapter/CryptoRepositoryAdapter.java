package com.logistics.paymentservice.infrastructure.adapter.out.database.adapter;

import com.logistics.paymentservice.application.port.out.crypto.CryptoTransactionPort;
import com.logistics.paymentservice.domain.model.CryptoTransaction;
import com.logistics.paymentservice.infrastructure.adapter.out.database.mapper.CryptoMapper;
import com.logistics.paymentservice.infrastructure.adapter.out.database.repository.CryptoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CryptoRepositoryAdapter implements CryptoTransactionPort {

    private final CryptoRepository cryptoRepository;

    @Override
    public void save(CryptoTransaction cryptoTransaction) {
        cryptoRepository.save(CryptoMapper.toCryptoEntity(cryptoTransaction));
    }

    @Override
    public Optional<CryptoTransaction> findByInvoiceId(String invoice_id){
        return cryptoRepository.findByInvoiceId(invoice_id).map(CryptoMapper::toCryptoDomain);
    }

    @Override
    public List<CryptoTransaction> findTransactionByUserId(String userId){
        return cryptoRepository.findByUserId(userId).stream()
                .map(CryptoMapper::toCryptoDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<CryptoTransaction> findAllTransaction(){
        return cryptoRepository.findAll().stream()
                .map(CryptoMapper::toCryptoDomain)
                .collect(Collectors.toList());
    }
}
