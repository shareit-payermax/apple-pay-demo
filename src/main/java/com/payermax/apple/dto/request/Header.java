package com.payermax.apple.dto.request;

import lombok.Data;

@Data
public class Header {
    private String applicationData;
    private String ephemeralPublicKey;
    private String wrappedKey;
    private String publicKeyHash;
    private String transactionId;
}
