package com.tmkcomputers.csms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import com.tmkcomputers.csms.model.FavoriteChargingStation;

public interface FavoriteChargingStationRepository extends JpaRepository<FavoriteChargingStation, Long> {
    List<FavoriteChargingStation> findByUserId(Long userId);
    void deleteByUserIdAndChargingStationId(Long userId, Long chargingStationId);
    Optional<FavoriteChargingStation> findByUserIdAndChargingStationId(Long userId, Long id);
}
