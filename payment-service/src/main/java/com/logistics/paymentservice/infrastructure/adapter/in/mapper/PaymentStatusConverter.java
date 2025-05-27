package com.logistics.paymentservice.infrastructure.adapter.in.mapper;

import org.springframework.core.convert.converter.Converter;
import com.logistics.paymentservice.domain.model.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class PaymentStatusConverter implements Converter<String, PaymentStatus> {

    @Override
    public PaymentStatus convert(String source) {
        return PaymentStatus.valueOf(source.toUpperCase());
    }
}
