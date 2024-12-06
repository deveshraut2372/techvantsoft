package com.tmkcomputers.csms.dto;

import com.tmkcomputers.csms.model.Amenity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AmenityDto {

    public AmenityDto(Amenity amenity) {
        this.id = amenity.getId();
        this.wifi = amenity.isWifi();
        this.foodcourt = amenity.isFoodcourt();
        this.washroom = amenity.isWashroom();
        this.children_playarea = amenity.isChildren_playarea();
        this.atm = amenity.isAtm();
    }

    private Long id;

    private boolean wifi;

    private boolean foodcourt;

    private boolean washroom;

    private boolean children_playarea;

    private boolean atm;
}
