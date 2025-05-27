package com.logistics.paymentservice.infrastructure.adapter.in.controller;

import com.logistics.paymentservice.application.port.in.PaymentsDetailsUseCase;
import com.logistics.paymentservice.domain.model.CryptoTransaction;
import com.logistics.paymentservice.domain.model.FiatTransaction;
import com.logistics.paymentservice.domain.model.UserBalance;
import com.logistics.paymentservice.infrastructure.adapter.in.dto.details.CryptoTransactionDTO;
import com.logistics.paymentservice.infrastructure.adapter.in.dto.details.FiatTransactionDTO;
import com.logistics.paymentservice.infrastructure.adapter.in.mapper.DetailsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/details")
@RequiredArgsConstructor
public class PaymentDetailsController {

    private final PaymentsDetailsUseCase paymentsDetailsUseCase;

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(String userId) {
        return ResponseEntity.ok(paymentsDetailsUseCase.getBalance(userId));
    }

    @GetMapping("all/balance")
    public ResponseEntity<List<UserBalance>> getAllBalance() {
        return ResponseEntity.ok(paymentsDetailsUseCase.getAllUserBalance());
    }

    @GetMapping("/crypto-transactions")
    public ResponseEntity<List<CryptoTransactionDTO>> getCryptoTransactions(@RequestHeader ("X-User-Id") String userId) {
        List<CryptoTransaction> cryptoTransactions = paymentsDetailsUseCase.getCryptoTransactions(userId);
        return ResponseEntity.ok(cryptoTransactions.stream().map(DetailsMapper::forUsersCryptoTransactionDTO).toList());
    }

    @GetMapping("/all/crypto-transactions")
    public ResponseEntity<List<CryptoTransactionDTO>> getAllCryptoTransactions() {
        List<CryptoTransaction> allCryptoTransactions = paymentsDetailsUseCase.getAllCryptoTransactions();
        return ResponseEntity.ok(allCryptoTransactions.stream().map(DetailsMapper::forAllCryptoTransactionDTO).toList());
    }

    @GetMapping("/fiat-transactions")
    public ResponseEntity<List<FiatTransactionDTO>> getFiatTransactions(@RequestHeader ("X-User-Id") String userId) {
        List<FiatTransaction> transactions = paymentsDetailsUseCase.getFiatTransactions(userId);
        return ResponseEntity.ok(transactions.stream().map(DetailsMapper::forUsersFiatTransactionDTO).toList());
    }

    @GetMapping("/all/fiat-transactions")
    public ResponseEntity<List<FiatTransactionDTO>> getAllFiatTransactions() {
        List<FiatTransaction> transactions = paymentsDetailsUseCase.getAllFiatTransactions();
        return ResponseEntity.ok(transactions.stream().map(DetailsMapper::forAllFiatTransactionDTO).toList());
    }

    @PostMapping("/pay-for-delivery")
    public ResponseEntity<String> payForDelivery(@RequestHeader ("X-User-Id") String userId, @RequestParam BigDecimal amount) {
        paymentsDetailsUseCase.payForDelivery(userId, amount);
        return ResponseEntity.ok("Payment for " + amount.toString());
    }

}
