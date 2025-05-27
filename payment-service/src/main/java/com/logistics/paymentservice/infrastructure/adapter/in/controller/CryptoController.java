package com.logistics.paymentservice.infrastructure.adapter.in.controller;

import com.logistics.paymentservice.application.port.in.CryptoPaymentUseCase;
import com.logistics.paymentservice.infrastructure.adapter.in.dto.crypto.PaymentRequest;
import com.logistics.paymentservice.infrastructure.adapter.in.dto.crypto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crypto")
@RequiredArgsConstructor
public class CryptoController {

    private final CryptoPaymentUseCase cryptoService;

    @PostMapping("/create-payment")
    public ResponseEntity<String> createPayment(
            @RequestHeader ("X-User-Id") String userID,
            @RequestBody PaymentRequest paymentRequest) {
        String link = cryptoService.createPayment(userID, paymentRequest.getAmount(), paymentRequest.getCurrency());
        return ResponseEntity.ok().body(link);
    }

    @PostMapping("/handle-payment")
    public ResponseEntity<Void> handlePayment(@ModelAttribute PaymentResponse response) {
        cryptoService.validateToken(response.token(), response.invoice_id());
        cryptoService.handlePayment(response.status(), response.invoice_id(), response.currency());
        return ResponseEntity.ok().build();
    }
}
