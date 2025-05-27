package com.logistics.paymentservice.webhook;

import com.logistics.paymentservice.application.service.CryptoService;
import com.logistics.paymentservice.domain.model.PaymentStatus;
import com.logistics.paymentservice.infrastructure.adapter.in.controller.CryptoController;
import com.logistics.paymentservice.infrastructure.adapter.in.dto.crypto.PaymentResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CryptoControllerTest {
    @Mock
    private CryptoService cryptoService;

    @InjectMocks
    private CryptoController paymentController;

    @Test
    void handlePayment_ShouldCallServicesAndReturnOk() {
        // given
        PaymentResponse response = new PaymentResponse(
                PaymentStatus.SUCCESS,
                "inv123",
                "USDT",
                "valid.jwt.token"
        );

        // when
        ResponseEntity<Void> result = paymentController.handlePayment(response);

        // then
        verify(cryptoService).validateToken("valid.jwt.token", "inv123");
        verify(cryptoService).handlePayment(PaymentStatus.SUCCESS, "inv123", "USDT");
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
