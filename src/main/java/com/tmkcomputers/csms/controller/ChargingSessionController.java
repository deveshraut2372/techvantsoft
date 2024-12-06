package com.tmkcomputers.csms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.tmkcomputers.csms.dto.BookingDto;
import com.tmkcomputers.csms.model.ChargingSession;
import com.tmkcomputers.csms.model.User;
import com.tmkcomputers.csms.service.ChargingSessionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/charging-sessions")
public class ChargingSessionController {

    @Autowired
    private ChargingSessionService chargingSessionService;

    @GetMapping
    public List<ChargingSession> getAllChargingSessions() {
        return chargingSessionService.getAllChargingSessions();
    }

    @GetMapping("/ongoing-bookings")
    public List<BookingDto> getOngoingBookingss(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return chargingSessionService.getOngoingBookingsOfUser(currentUser.getId());
    }

    @GetMapping("/history-bookings")
    public List<BookingDto> getHistoryBookingss(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return chargingSessionService.getHistoryBookingsOfUser(currentUser.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargingSession> getChargingSessionById(@PathVariable Long id) {
        Optional<ChargingSession> chargingSession = chargingSessionService.getChargingSessionById(id);
        return chargingSession.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ChargingSession createChargingSession(@RequestBody ChargingSession chargingSession) {
        return chargingSessionService.createChargingSession(chargingSession);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChargingSession> updateChargingSession(
            @PathVariable Long id,
            @RequestBody ChargingSession chargingSession) {
        ChargingSession updatedSession = chargingSessionService.updateChargingSession(id, chargingSession);
        if (updatedSession != null) {
            return ResponseEntity.ok(updatedSession);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChargingSession(@PathVariable Long id) {
        chargingSessionService.deleteChargingSession(id);
        return ResponseEntity.noContent().build();
    }
}
