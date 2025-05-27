package com.logistics.paymentservice.infrastructure.adapter.out.database.repository;

import com.logistics.paymentservice.domain.model.FiatTransaction;
import com.logistics.paymentservice.infrastructure.adapter.out.database.entity.FiatTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FiatRepository extends JpaRepository<FiatTransactionEntity, Long> {
    Optional<FiatTransactionEntity> findByInvoiceId(String invoiceId);
    List<FiatTransactionEntity> findByUserId(String userId);
}
