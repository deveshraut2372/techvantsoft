package com.tmkcomputers.csms.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConnectorSummaryDto {

    public ConnectorSummaryDto(String connectionType, Long totalSlot, Long takenSlot, BigDecimal capacity, BigDecimal pricePerWatt) {
        this.connectionType = connectionType;
        this.totalSlot = totalSlot;
        this.takenSlot = takenSlot;
        this.capacity = capacity;
        this.pricePerWatt = pricePerWatt;
    }

    private int id;
    private String connectionTypeImage;
    private String connectionType;
    private BigDecimal capacity;
    private BigDecimal pricePerWatt;
    private Long takenSlot;
    private Long totalSlot;
}
