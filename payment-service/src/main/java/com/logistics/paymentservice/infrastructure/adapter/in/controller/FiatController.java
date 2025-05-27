package com.logistics.paymentservice.infrastructure.adapter.in.controller;

import com.logistics.paymentservice.application.port.in.FiatPaymentUseCase;
import com.logistics.paymentservice.infrastructure.adapter.in.dto.fiat.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fiat")
@RequiredArgsConstructor
public class FiatController {

    private final FiatPaymentUseCase fiatPaymentUseCase;

    @PostMapping("/create-payment")
    public ResponseEntity<String> createPayment(@RequestHeader ("X-User-Id") String userId,
                                                @RequestBody PaymentRequest paymentRequest
                                                ){
        String paymentLink = fiatPaymentUseCase.createPayment(userId, paymentRequest.amount(), paymentRequest.currency());
        return ResponseEntity.ok().body(paymentLink);
    }

    @PostMapping("/handle-payment")
    public ResponseEntity<Void> handlePayment(@RequestHeader("Stripe-Signature") String sigHeader,
                                              @RequestBody String payload){
        fiatPaymentUseCase.validatePayment(sigHeader, payload);
        fiatPaymentUseCase.handlePayment(payload);
        return ResponseEntity.ok().build();
    }

}
