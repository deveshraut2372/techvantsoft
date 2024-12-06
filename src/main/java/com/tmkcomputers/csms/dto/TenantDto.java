package com.tmkcomputers.csms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TenantDto {
    private long id;

    private String name;

    private String description;

    private String headOfficeAddress;

    private long noOfChargingStations;

    private long noOfChargers;
}
