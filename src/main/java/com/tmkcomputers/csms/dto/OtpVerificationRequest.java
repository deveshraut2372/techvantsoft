package com.tmkcomputers.csms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OtpVerificationRequest {
    private String mobileNumber;
    private String otp;
}
