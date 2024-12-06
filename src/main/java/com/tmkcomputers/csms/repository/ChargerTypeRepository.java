package com.tmkcomputers.csms.repository;

import com.tmkcomputers.csms.model.ChargerType;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargerTypeRepository extends JpaRepository<ChargerType, Long> {

    Optional<ChargerType> findByName(String name);
}
