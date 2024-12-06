package com.tmkcomputers.csms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrouteChargingStationsRequest {
    private String sourcePlace;
    private String destinationPlace;
}
