package com.logistics.paymentservice.infrastructure.config.fiat;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class StripeConfig {

    @Value("${stripe.api.secret}")
    private String apiSecretKey;

    @Value("${stripe.api.webhook-secret}")
    private String webhookKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = apiSecretKey;
    }
}
