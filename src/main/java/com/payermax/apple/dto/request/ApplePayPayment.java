package com.payermax.apple.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * https://developer.apple.com/documentation/apple_pay_on_the_web/applepaypayment
 */
@Data
public class ApplePayPayment implements Serializable {
    private static final long serialVersionUID = -5963641789910685296L;
    private ApplePayPaymentToken token;
    ApplePayPaymentContact billingContact;
    ApplePayPaymentContact shippingContact;
}
