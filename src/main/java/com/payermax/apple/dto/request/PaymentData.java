package com.payermax.apple.dto.request;

import lombok.Data;

@Data
public class PaymentData {
    private String data;
    private Header header;
    private String signature;
    private String version;
}
