package com.payermax.apple.dto.request;

import lombok.Data;

@Data
public class ApplePayPaymentToken {
    private ApplePayPaymentMethod paymentMethod;
    private String transactionIdentifier;
    private PaymentData paymentData;
}
