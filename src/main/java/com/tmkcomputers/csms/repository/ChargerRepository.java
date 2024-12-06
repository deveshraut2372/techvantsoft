package com.tmkcomputers.csms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tmkcomputers.csms.model.Charger;

import java.util.List;

public interface ChargerRepository extends JpaRepository<Charger, Long> {
    List<Charger> findByChargingStationId(Long chargingStationId);
}
