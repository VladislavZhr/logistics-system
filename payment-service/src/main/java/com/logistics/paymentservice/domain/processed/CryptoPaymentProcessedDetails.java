package com.logistics.paymentservice.domain.processed;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CryptoPaymentProcessedDetails {
    private String pay_url;
    @JsonProperty("invoice_id")
    private String invoiceId;
}
