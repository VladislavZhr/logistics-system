package com.logistics.paymentservice.infrastructure.config.crypto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class CryptoCloudConfig {
    @Value("${cryptocloud.api.url}")
    private String apiUrl;
    @Value("${cryptocloud.api.key}")
    private String apiKey;
    @Value("${cryptocloud.shop.id}")
    private String shopId;
    @Value("${cryptocloud.secret}")
    private String secretKey;
}
