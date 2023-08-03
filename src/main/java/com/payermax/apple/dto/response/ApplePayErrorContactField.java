package com.payermax.apple.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ApplePayErrorContactField {
    private String phoneNumber;
    private String emailAddress;
    private String name;
    private String phoneticName;
    private String postalAddress;
    private List<String> addressLines;
    private String locality;
    private String subLocality;
    private String postalCode;
    private String administrativeArea;
    private String subAdministrativeArea;
    private String country;
    private String countryCode;
}
