package com.tmkcomputers.csms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DayOperationalHourDto {
    private String day;
    @JsonProperty("isOpen")
    private boolean isOpen;
    private OperationalTimingDto operationalTiming;
}
