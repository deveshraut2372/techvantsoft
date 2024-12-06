package com.tmkcomputers.csms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;

    private String refreshToken;

    private long expiresIn;
}
