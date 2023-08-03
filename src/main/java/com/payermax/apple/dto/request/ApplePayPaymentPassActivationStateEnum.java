package com.payermax.apple.dto.request;

public enum ApplePayPaymentPassActivationStateEnum {
    activated("activated"),//Active and ready to be used for payment.

    requiresActivation("requiresActivation"),//Not active but may be activated by the issuer.

    activating("activating"),//Not ready for use but activation is in progress.

    suspended("suspended"),//Not active and can't be activated.

    deactivated("deactivated"),//Not active because the issuer has disabled the account associated with the device.

    ;

    private String value;

    ApplePayPaymentPassActivationStateEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
