package com.tmkcomputers.csms.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChargingStationResponseDto {
    private long id;
    private String stationImage;
    private String stationName;
    private String stationAddress;
    private double rating;
    private double distance;
    private long totalPoints;
    private double latitude;
    private double longitude;
    @JsonProperty("isOpen")
    private boolean isOpen;
    @JsonProperty("isOpen24x7")
    private boolean isOpen24x7;

    private List<DayOperationalHourDto> dailyOperationalHours;
}
