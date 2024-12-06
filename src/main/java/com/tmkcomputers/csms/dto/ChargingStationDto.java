package com.tmkcomputers.csms.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChargingStationDto {

    public ChargingStationDto() {
    }

    public ChargingStationDto(long id, String name, String description, String address, String pincode, 
                        Double latitude, Double longitude, boolean open, long noOfChargers, long noOfConnectors) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.pincode = pincode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.open = open;
        this.noOfChargers = noOfChargers;
        this.noOfConnectors = noOfConnectors;
    }

    private long id;

    private String name;

    private String description;

    private String address;

    private String pincode;

    private Double latitude;

    private Double longitude;

    private boolean open;

    private boolean favorite;

    private double distance;

    private long noOfChargers;

    private long noOfConnectors;

    private Long tenantId;

    private AmenityDto amenity;

    @JsonProperty("isOpen24x7")
    private boolean isOpen24x7;

    private List<DayOperationalHourDto> operationalHours;
}
