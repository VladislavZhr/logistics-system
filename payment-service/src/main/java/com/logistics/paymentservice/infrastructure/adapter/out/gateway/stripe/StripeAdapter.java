package com.logistics.paymentservice.infrastructure.adapter.out.gateway.stripe;

import com.logistics.paymentservice.application.port.out.fiat.StripePort;
import com.logistics.paymentservice.domain.processed.FiatHandleProcessedDetails;
import com.logistics.paymentservice.domain.processed.FiatPaymentProcessedDetails;
import com.logistics.paymentservice.infrastructure.config.fiat.StripeConfig;
import com.logistics.paymentservice.infrastructure.exceptions.error.StripePaymentException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;

import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class StripeAdapter implements StripePort {

    private final StripeConfig stripeConfig;

    private Event latestValidatedEvent;

    @Override
    public FiatPaymentProcessedDetails processCreateLink(String userId, String currency, BigDecimal amount) {
        try {
            long amountInSmallestUnit = amount.multiply(BigDecimal.valueOf(100)).longValue();

            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("https://your-domain.com/success?session_id={CHECKOUT_SESSION_ID}")
                    .setCancelUrl("https://your-domain.com/cancel")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency(currency)
                                                    .setUnitAmount(amountInSmallestUnit)
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Logistics Order Payment")
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .setPaymentIntentData(
                            SessionCreateParams.PaymentIntentData.builder()
                                    .putMetadata("userId", userId)
                                    .build()
                    )
                    .putMetadata("userId", userId)
                    .build();

            Session session = Session.create(params);

            return new FiatPaymentProcessedDetails(session.getUrl(), session.getId());

        } catch (StripeException e) {
            throw new StripePaymentException("Stripe payment failed: " + e.getMessage());
        }
    }


    @Override
    public void validatePayment(String sigHeader, String payload){
        try {
            latestValidatedEvent = Webhook.constructEvent(payload, sigHeader, stripeConfig.getWebhookKey());
        } catch (SignatureVerificationException e) {
            throw new StripePaymentException("Signature verification failed: " + e.getMessage());
        }
    }

    @Override
    public FiatHandleProcessedDetails processHandlePayment(String payload){
        validateEvent();

        Session session = (Session) latestValidatedEvent.getDataObjectDeserializer()
                .getObject()
                .orElseThrow(() -> new StripePaymentException("Cannot deserialize session"));

        String userId = session.getMetadata().get("userId");
        String sessionId = session.getId(); // Це було збережене як invoiceId
        String invoiceId = session.getPaymentIntent();
        return new FiatHandleProcessedDetails(sessionId, invoiceId, userId);
    }

    private void validateEvent(){
        if (latestValidatedEvent == null) {
            throw new StripePaymentException("No validated Stripe event found");
        }

        if (!"checkout.session.completed".equals(latestValidatedEvent.getType())) {
            throw new StripePaymentException("Stripe payment failed: " + latestValidatedEvent.getType());
        }
    }
}
