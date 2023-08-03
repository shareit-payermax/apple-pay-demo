package com.payermax.apple.decrypt;

import lombok.Data;

@Data
public class DetailedPaymentData {
    // Detailed Payment Data Keys (3-D Secure)
    private String onlinePaymentCryptogram;
    private String eciIndicator;

    // Detailed Payment Data Keys (EMV)
    private String emvData;
    private String encryptedPINData;
}
