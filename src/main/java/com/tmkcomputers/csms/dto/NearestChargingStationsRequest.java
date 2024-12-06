package com.tmkcomputers.csms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NearestChargingStationsRequest {
    private double latitude;
    private double longitude;
    private double distanceThreshold;
}
