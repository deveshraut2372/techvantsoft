package com.tmkcomputers.csms.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "chargingSessionId")
    private ChargingSession chargingSession;

    private int easeOfUseRating;
    private int safetyRating;
    private int amenitiesRating;
    private int chargingSpeedRating;
    private String reviewComment;
    private double averageRating;
    private LocalDateTime dateTime;
}
