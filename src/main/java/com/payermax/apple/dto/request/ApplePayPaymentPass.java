package com.payermax.apple.dto.request;

import lombok.Data;

@Data
public class ApplePayPaymentPass {
    private String primaryAccountIdentifier;
    private String primaryAccountNumberSuffix;
    private String deviceAccountIdentifier;
    private String deviceAccountNumberSuffix;
    /**
     * @see ApplePayPaymentPassActivationStateEnum
     */
    private String activationState;
}
