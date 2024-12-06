package com.tmkcomputers.csms.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "amenities")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Amenity {

    public Amenity() {
        wifi = true;
        foodcourt = true;
        washroom = true;
        children_playarea = true;
        atm = true;
    }

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "amenity", cascade = CascadeType.ALL)
    @JsonBackReference
    private ChargingStation chargingStation;

    private boolean wifi;

    private boolean foodcourt;

    private boolean washroom;

    private boolean children_playarea;

    private boolean atm;
}
