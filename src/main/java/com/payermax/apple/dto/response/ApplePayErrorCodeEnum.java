package com.payermax.apple.dto.response;

public enum ApplePayErrorCodeEnum {
    /**
     * The code that indicates that the shipping address or contact information is invalid or missing.
     * Use with contactField.
     */
    shippingContactInvalid("shippingContactInvalid"),
    /**
     * The code that indicates that the billing address information is invalid or missing.
     * Use with contactField.
     */
    billingContactInvalid("billingContactInvalid"),
    /**
     * The code that indicates that the merchant can’t provide service to the shipping address (for example, can’t deliver to a P.O. Box).
     */
    addressUnserviceable("addressUnserviceable"),
    /**
     * The code that indicates an invalid coupon.
     */
    couponCodeInvalid("couponCodeInvalid"),
    /**
     * The code that indicates an expired coupon.
     */
    couponCodeExpired("couponCodeExpired"),
    /**
     * The code that indicates an unknown but nonfatal error occurred during payment processing. The user can attempt authorization again.
     */
    unknown("unknown"),
    ;

    private String value;

    ApplePayErrorCodeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
