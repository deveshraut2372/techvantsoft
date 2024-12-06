package com.tmkcomputers.csms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmkcomputers.csms.dto.BookingDto;
import com.tmkcomputers.csms.model.ChargingSession;
import com.tmkcomputers.csms.repository.ChargingSessionRepository;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Optional;

@Service
public class ChargingSessionService {

    @Autowired
    private ChargingSessionRepository chargingSessionRepository;

    public List<ChargingSession> getAllChargingSessions() {
        return chargingSessionRepository.findAll();
    }

    public Optional<ChargingSession> getChargingSessionById(Long id) {
        return chargingSessionRepository.findById(id);
    }

    public ChargingSession createChargingSession(ChargingSession chargingSession) {
        return chargingSessionRepository.save(chargingSession);
    }

    public ChargingSession updateChargingSession(Long id, ChargingSession chargingSession) {
        Optional<ChargingSession> existingSession = chargingSessionRepository.findById(id);
        if (existingSession.isPresent()) {
            chargingSession.setId(id);
            return chargingSessionRepository.save(chargingSession);
        }
        return null;
    }

    public void deleteChargingSession(Long id) {
        chargingSessionRepository.deleteById(id);
    }

    public List<BookingDto> getOngoingBookingsOfUser(Long userId) {
        var bookings = chargingSessionRepository.getOngoingBookingsOfUser(userId);
        for (BookingDto bookingDto : bookings) {
            var scheduledDateTime = bookingDto.getScheduledDateTime();

            DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEE");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

            bookingDto.setBookingDay(scheduledDateTime.format(dayFormatter));
            bookingDto.setBookingTime(scheduledDateTime.format(timeFormatter).toUpperCase());
            bookingDto.setChargingStationImage("charging_station2.png");
        }
        return bookings;
    }

    public List<BookingDto> getHistoryBookingsOfUser(Long userId) {
        var bookings = chargingSessionRepository.getHistoryBookingsOfUser(userId);
        for (BookingDto bookingDto : bookings) {
            var scheduledDateTime = bookingDto.getScheduledDateTime();
            bookingDto.setBookingDate(scheduledDateTime.toLocalDate().toString());
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
            bookingDto.setBookingTime(scheduledDateTime.format(timeFormatter).toUpperCase());
            bookingDto.setChargingStationImage("charging_station3.png");
        }
        return bookings;
    }
}
