package com.tmkcomputers.csms.repository;

import com.tmkcomputers.csms.model.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VehicleModelRepository extends JpaRepository<VehicleModel, Long> {
    List<VehicleModel> findByVehicleMakeId(Long vehicleMakeId);
}
