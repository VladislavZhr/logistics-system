package com.logistics.paymentservice.infrastructure.adapter.out.gateway.cryptocloud;

import com.logistics.paymentservice.application.port.out.crypto.CryptoCloudPort;
import com.logistics.paymentservice.domain.processed.CryptoPaymentProcessedDetails;
import com.logistics.paymentservice.infrastructure.config.crypto.CryptoCloudConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Component
public class CryptoCloudAdapter implements CryptoCloudPort {

    private final RestTemplate restTemplate;
    private final CryptoCloudConfig config;

    @Override
    public CryptoPaymentProcessedDetails processCreateLink(String userId, BigDecimal amount, String currency){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Token " + config.getApiKey());

        Map<String, Object> paymentBody = new HashMap<>();
        paymentBody.put("shop_id", config.getShopId());
        paymentBody.put("email", userId);
        paymentBody.put("amount", amount);
        paymentBody.put("currency", currency);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paymentBody, headers);
        return restTemplate.postForObject(config.getApiUrl(), request, CryptoPaymentProcessedDetails.class);
    }
}
