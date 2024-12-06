package com.tmkcomputers.csms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateManagerRequest {
    private String email;

    private String password;

    private String fullName;
    
    private String mobileNumber;

    private long entityId;
}
