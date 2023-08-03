package com.payermax.apple.dto.response;

import lombok.Data;

@Data
public class ApplePayError {
    /**
     * @see ApplePayErrorCodeEnum
     */
    private String code;
    /**
     * @see ApplePayErrorContactField
     */
    private String contactField;
    private String message;
}
