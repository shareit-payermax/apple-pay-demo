package com.payermax.apple.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * https://developer.apple.com/documentation/apple_pay_on_the_web/applepaypaymentauthorizationresult
 */
@Data
public class ApplePayPaymentAuthorizationResult implements Serializable {
    private static final long serialVersionUID = 3986064623179272291L;
    /**
     * @see ApplePayStatusEnum
     */
    private int status;
    private List<ApplePayError> errors;
}
