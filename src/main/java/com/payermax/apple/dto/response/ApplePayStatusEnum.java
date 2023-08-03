package com.payermax.apple.dto.response;

public enum ApplePayStatusEnum {
    STATUS_SUCCESS(0),
    STATUS_FAILURE(1),
    ;

    private int value;

    ApplePayStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
