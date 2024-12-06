package com.tmkcomputers.csms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserVehicleDto {

    private Long vehicleModelId;

    private Long connectorTypeId;

    private String registrationNumber;
}
