package com.tmkcomputers.csms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tmkcomputers.csms.model.VehicleMake;

public interface VehicleMakeRepository extends JpaRepository<VehicleMake, Long> {

    Optional<VehicleMake> findByName(String name);
}
