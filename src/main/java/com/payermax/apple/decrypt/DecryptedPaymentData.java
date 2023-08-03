package com.payermax.apple.decrypt;

import lombok.Data;

@Data
public class DecryptedPaymentData {
    private String applicationPrimaryAccountNumber;
    private String applicationExpirationDate;
    private String currencyCode;
    private String transactionAmount;
    private String cardholderName;
    private String deviceManufacturerIdentifier;
    private String paymentDataType;
    private DetailedPaymentData paymentData;
}
