package com.payermax.apple.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ApplePayPaymentContact {
    private String phoneNumber;
    private String emailAddress;
    private String givenName;
    private String familyName;
    private String phoneticGivenName;
    private String phoneticFamilyName;
    private List<String> addressLines;
    private String subLocality;
    private String locality;
    private String postalCode;
    private String subAdministrativeArea;
    private String administrativeArea;
    private String country;
    private String countryCode;
}
