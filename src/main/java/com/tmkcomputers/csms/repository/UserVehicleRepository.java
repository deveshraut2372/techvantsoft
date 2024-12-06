package com.tmkcomputers.csms.repository;

import com.tmkcomputers.csms.model.UserVehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserVehicleRepository extends JpaRepository<UserVehicle, Long> {
    List<UserVehicle> findAllByUserId(Long userId);
}
