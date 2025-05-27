package com.logistics.paymentservice.domain.processed;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FiatPaymentProcessedDetails {
    private String payUrl;
    private String invoiceId;
}
