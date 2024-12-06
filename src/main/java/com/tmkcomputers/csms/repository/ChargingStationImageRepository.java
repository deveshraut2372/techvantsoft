package com.tmkcomputers.csms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tmkcomputers.csms.model.ChargingStationImage;

public interface ChargingStationImageRepository extends JpaRepository<ChargingStationImage, Long> {
    List<ChargingStationImage> findByChargingStationId(Long chargingStationId);
}
