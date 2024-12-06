package com.tmkcomputers.csms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
    private String email;

    private String password;

    private String fullName;
    
    private String mobileNumber;

    private String authProvider;
}
