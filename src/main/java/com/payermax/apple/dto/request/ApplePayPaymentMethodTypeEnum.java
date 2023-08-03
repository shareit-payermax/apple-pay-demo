package com.payermax.apple.dto.request;

public enum ApplePayPaymentMethodTypeEnum {
    debit("debit"),
    credit("credit"),
    prepaid("prepaid"),
    store("store"),
    ;

    private String value;

    ApplePayPaymentMethodTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
