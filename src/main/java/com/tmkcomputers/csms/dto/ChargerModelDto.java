package com.tmkcomputers.csms.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChargerModelDto {
    private String modelNumber;

    private String modelName;

    private BigDecimal maxPower;

    private BigDecimal voltage;

    private BigDecimal current;

    private Long chargerTypeId;
    
    private Long chargerOemId;
}
