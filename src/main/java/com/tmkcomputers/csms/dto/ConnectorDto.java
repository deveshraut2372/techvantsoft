package com.tmkcomputers.csms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectorDto {
    private String name;

    private String description;

    private Long chargerId;

    private Long connectorTypeId;
}
