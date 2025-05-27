package com.logistics.paymentservice.infrastructure.adapter.out.database.repository;

import com.logistics.paymentservice.infrastructure.adapter.out.database.entity.CryptoTransactionEntity;
import com.logistics.paymentservice.infrastructure.adapter.out.database.entity.UserBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CryptoRepository extends JpaRepository<CryptoTransactionEntity, Long>{
    Optional<CryptoTransactionEntity> findByInvoiceId(String invoiceId);


    List<CryptoTransactionEntity> findByUserId(String userId);
}
