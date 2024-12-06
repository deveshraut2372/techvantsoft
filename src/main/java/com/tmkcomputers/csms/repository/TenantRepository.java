package com.tmkcomputers.csms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tmkcomputers.csms.dto.TenantDto;
import com.tmkcomputers.csms.model.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

    @Query("SELECT new com.tmkcomputers.csms.dto.TenantDto(h.id, h.name, h.description, h.headOfficeAddress, COUNT(DISTINCT p), COUNT(c)) " +
           "FROM Tenant h " +
           "LEFT JOIN h.chargingStations p " +
           "LEFT JOIN p.chargers c " +
           "GROUP BY h.id")
    List<TenantDto> findTenantChargingStationChargerStats();

    @Query("SELECT new com.tmkcomputers.csms.dto.TenantDto(h.id, h.name, h.description, h.headOfficeAddress, COUNT(DISTINCT p), COUNT(c)) " +
           "FROM Tenant h " +
           "LEFT JOIN h.chargingStations p " +
           "LEFT JOIN p.chargers c " +
           "WHERE h.id = :tenantId " +
           "GROUP BY h.id")
    Optional<TenantDto> findTenantChargingStationChargerStatsById(@Param("tenantId") Long tenantId);
}
