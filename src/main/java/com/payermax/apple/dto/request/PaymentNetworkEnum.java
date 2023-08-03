package com.payermax.apple.dto.request;

/**
 * https://developer.apple.com/documentation/apple_pay_on_the_web/applepaypaymentrequest/1916122-supportednetworks
 */
public enum PaymentNetworkEnum {
    amex("amex"),
    cartesBancaires("cartesBancaires"),
    chinaUnionPay("chinaUnionPay"),
    discover("discover"),
    eftpos("eftpos"),
    electron("electron"),
    elo("elo"),
    interac("interac"),
    jcb("jcb"),
    mada("mada"),
    maestro("maestro"),
    masterCard("masterCard"),
    privateLabel("privateLabel"),
    visa("visa"),
    vPay("vPay"),

    ;

    private String value;

    PaymentNetworkEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
