package com.tmkcomputers.csms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tmkcomputers.csms.dto.ChargingStationDto;
import com.tmkcomputers.csms.dto.ConnectorSummaryDto;
import com.tmkcomputers.csms.dto.SessionReviewDto;
import com.tmkcomputers.csms.model.ChargingStation;

import java.util.List;
import java.util.Optional;

public interface ChargingStationRepository extends JpaRepository<ChargingStation, Long> {
       List<ChargingStation> findByTenantId(Long tenantId);

       List<ChargingStation> findAllByOpenIsTrue();

       @Query("SELECT new com.tmkcomputers.csms.dto.ChargingStationDto(p.id, p.name, p.description, p.address, p.pincode, p.latitude, p.longitude, p.open, COUNT(DISTINCT ch), COUNT(cn)) "
                     +
                     "FROM ChargingStation p " +
                     "LEFT JOIN p.chargers ch " +
                     "LEFT JOIN ch.connectors cn " +
                     "WHERE p.id = :chargingStationId " +
                     "GROUP BY p.id")
       Optional<ChargingStationDto> findChargingStationConnectorStatsById(
                     @Param("chargingStationId") Long chargingStationId);

       @Query("SELECT new com.tmkcomputers.csms.dto.ChargingStationDto(p.id, p.name, p.description, p.address, p.pincode, p.latitude, p.longitude, p.open, COUNT(DISTINCT ch), COUNT(cn)) "
                     +
                     "FROM ChargingStation p " +
                     "LEFT JOIN p.chargers ch " +
                     "LEFT JOIN ch.connectors cn " +
                     "WHERE p.id IN :chargingStationIds " +
                     "GROUP BY p.id")
       List<ChargingStationDto> findChargingStationConnectorStatsForGivenIds(
                     @Param("chargingStationIds") List<Long> chargingStationIds);

       // Custom query to fetch Charger with Connectors
       @Query("SELECT new com.tmkcomputers.csms.dto.ConnectorSummaryDto(ct.name, COUNT(conn) AS totalCount, SUM(CASE WHEN conn.taken = true THEN 1 ELSE 0 END) AS takenCount, cm.maxPower, c.pricePerWatt) FROM Charger c "
                     +
                     "JOIN c.connectors conn " +
                     "JOIN conn.connectorType ct " +
                     "JOIN c.chargerModel cm " +
                     "WHERE c.chargingStation.id = :chargingStationId " +
                     "GROUP BY ct.name, cm.maxPower, c.pricePerWatt")
       List<ConnectorSummaryDto> findByChargingStationIdWithConnectors(
                     @Param("chargingStationId") Long chargingStationId);

       @Query("SELECT new com.tmkcomputers.csms.dto.SessionReviewDto(cs.user.fullName, sr.averageRating, sr.reviewComment) FROM SessionReview sr " +
                     "JOIN sr.chargingSession cs " +
                     "JOIN cs.connector co " +
                     "JOIN co.charger ch " +
                     "JOIN ch.chargingStation s " +
                     "WHERE s.id = :chargingStationId")
       List<SessionReviewDto> findByChargingStationIdWithReviews(@Param("chargingStationId") Long chargingStationId);

       @Query("SELECT COUNT(cn) " +
                     "FROM ChargingStation p " +
                     "LEFT JOIN p.chargers ch " +
                     "LEFT JOIN ch.connectors cn " +
                     "WHERE p.id = :chargingStationId " +
                     "GROUP BY p.id")
       long getNumberOfConnectors(@Param("chargingStationId") Long chargingStationId);

    Optional<ChargingStation> findByName(String string);
}
