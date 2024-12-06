package com.tmkcomputers.csms.model;


import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChargingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "userVehicleId", nullable = false)
    private UserVehicle userVehicle;

    @ManyToOne
    @JoinColumn(name = "connectorId", nullable = false)
    private Connector connector;

    @OneToOne(mappedBy = "chargingSession", cascade = CascadeType.ALL)
    private SessionReview sessionReview;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private double powerConsumed;
    private double totalBill;

    private boolean prebooked = false;
    private LocalDateTime scheduledDateTime;
    private LocalDateTime prebookedAt;
    private boolean cancelled = false;
    private boolean completed = false;
    private boolean failed = false;
    private boolean cancelledByUser = false;
    private boolean completedByUser = false;
    private boolean failedByUser = false;
}
