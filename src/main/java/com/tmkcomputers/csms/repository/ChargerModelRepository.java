package com.tmkcomputers.csms.repository;

import com.tmkcomputers.csms.model.ChargerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChargerModelRepository extends JpaRepository<ChargerModel, Long> {
    List<ChargerModel> findByChargerTypeIdAndChargerOemId(Long chargerTypeId, Long chargerOemId);
}
