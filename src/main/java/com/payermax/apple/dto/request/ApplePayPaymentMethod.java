package com.payermax.apple.dto.request;

import lombok.Data;

@Data
public class ApplePayPaymentMethod {
    private String displayName;
    private String network;
    /**
     * @see ApplePayPaymentMethodTypeEnum
     */
    private String type;
    private ApplePayPaymentPass paymentPass;
    private ApplePayPaymentContact billingContact;
}
