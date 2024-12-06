package com.tmkcomputers.csms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckUserExistsResponse {
    private boolean success;
    private boolean exists;
    private String message;
}
