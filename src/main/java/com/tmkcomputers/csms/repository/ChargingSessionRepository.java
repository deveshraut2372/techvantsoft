package com.tmkcomputers.csms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.tmkcomputers.csms.dto.BookingDto;
import com.tmkcomputers.csms.model.ChargingSession;

@Repository
public interface ChargingSessionRepository extends JpaRepository<ChargingSession, Long> {

    @Query("SELECT new com.tmkcomputers.csms.dto.BookingDto(cs.id, st.name, st.address, cs.scheduledDateTime) FROM ChargingSession cs "
            +
            "JOIN cs.connector c " +
            "JOIN c.charger ch " +
            "JOIN ch.chargingStation st " +
            "JOIN cs.user u " +
            "WHERE cs.scheduledDateTime > CURRENT_TIMESTAMP " +
            "AND u.id = :userId")
    List<BookingDto> getOngoingBookingsOfUser(@Param("userId") Long userId);

    @Query("SELECT new com.tmkcomputers.csms.dto.BookingDto(cs.id, st.name, st.address, cs.scheduledDateTime) FROM ChargingSession cs "
            +
            "JOIN cs.connector c " +
            "JOIN c.charger ch " +
            "JOIN ch.chargingStation st " +
            "JOIN cs.user u " +
            "WHERE cs.scheduledDateTime < CURRENT_TIMESTAMP " +
            "AND u.id = :userId")
    List<BookingDto> getHistoryBookingsOfUser(@Param("userId") Long userId);
}
