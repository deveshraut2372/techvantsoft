package com.tmkcomputers.csms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChargerDto {
    private Long id;
    
    private String name;

    private String description;

    private Long chargingStationId;

    private Long chargerModelId;

    private Long chargerTypeId;

    private Long chargerOemId;
}
