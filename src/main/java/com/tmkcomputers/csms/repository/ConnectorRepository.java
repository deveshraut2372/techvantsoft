package com.tmkcomputers.csms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tmkcomputers.csms.model.Connector;

import java.util.List;

public interface ConnectorRepository extends JpaRepository<Connector, Long> {
    List<Connector> findByChargerId(Long chargerId);
}
