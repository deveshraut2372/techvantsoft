package com.tmkcomputers.csms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tmkcomputers.csms.model.ChargerOem;

public interface ChargerOemRepository extends JpaRepository<ChargerOem, Long> {

    Optional<ChargerOem> findByName(String name);
}
