package com.tmkcomputers.csms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OtpVerificationResponse {
    private boolean success;
    private String message;
    private LoginResponse loginResponse;
    private UserProfileResponse userProfileResponse;
}
