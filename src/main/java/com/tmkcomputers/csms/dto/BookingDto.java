package com.tmkcomputers.csms.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private long id;
    private String chargingStationImage;
    private String chargingStationName;
    private String chargingStationAddress;
    private String bookingDay;
    private String bookingDate;
    private String bookingTime;
    private LocalDateTime scheduledDateTime;

    public BookingDto(long id, String chargingStationName, String chargingStationAddress, LocalDateTime scheduledDateTime) {
        this.id = id;
        this.chargingStationName = chargingStationName;
        this.chargingStationAddress = chargingStationAddress;
        this.scheduledDateTime = scheduledDateTime;
    }
}
