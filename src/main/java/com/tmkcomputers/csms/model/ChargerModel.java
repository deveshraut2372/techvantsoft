package com.tmkcomputers.csms.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ChargerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model_number", nullable = false, length = 100)
    private String modelNumber;

    @Column(name = "model_name", nullable = false, length = 255)
    private String modelName;

    @Column(name = "max_power", precision = 5, scale = 2)
    private BigDecimal maxPower;

    @Column(name = "voltage", precision = 5, scale = 2)
    private BigDecimal voltage;

    @Column(name = "current", precision = 5, scale = 2)
    private BigDecimal current;

    @ManyToOne
    @JoinColumn(name = "chargerTypeId", nullable = false)
    @JsonProperty("chargerTypeId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private ChargerType chargerType;

    @ManyToOne
    @JoinColumn(name = "chargerOemId", nullable = false)
    @JsonProperty("chargerOemId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private ChargerOem chargerOem;
}
