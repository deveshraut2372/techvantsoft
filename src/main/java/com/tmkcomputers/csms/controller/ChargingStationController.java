package com.tmkcomputers.csms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.tmkcomputers.csms.dto.ChargerDto;
import com.tmkcomputers.csms.dto.ChargingStationDto;
import com.tmkcomputers.csms.dto.ChargingStationResponseDto;
import com.tmkcomputers.csms.dto.ConnectorSummaryDto;
import com.tmkcomputers.csms.dto.EnrouteChargingStationsRequest;
import com.tmkcomputers.csms.dto.NearestChargingStationsRequest;
import com.tmkcomputers.csms.dto.SessionReviewDto;
import com.tmkcomputers.csms.model.ChargingStation;
import com.tmkcomputers.csms.model.User;
import com.tmkcomputers.csms.service.ChargerService;
import com.tmkcomputers.csms.service.ChargingStationImageService;
import com.tmkcomputers.csms.service.ChargingStationService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/charging-stations")
public class ChargingStationController {

    @Autowired
    private ChargingStationService chargingStationService;
    @Autowired
    private ChargerService chargerService;
    @Autowired
    private ChargingStationImageService imageService;

    @GetMapping("/{id}/chargers")
    public List<ChargerDto> getAllChargingStationChargers(@PathVariable Long id) {
        return chargerService.getChargersByChargingStationId(id);
    }

    @GetMapping("/connectors/{id}")
    public List<ConnectorSummaryDto> getAllChargingStationConnectors(@PathVariable Long id) {
        var connectors = chargerService.getConnectorsByChargingStationId(id);

        int i = 1;
        for (ConnectorSummaryDto connector : connectors) {
            connector.setId(i++);
            connector.setConnectionTypeImage("connection_type" + i + ".png");
        }

        return connectors;
    }

    @GetMapping("/customer-reviews/{id}")
    public List<SessionReviewDto> getAllChargingStationCustomerReviews(@PathVariable Long id) {
        var reviews = chargerService.getCustomerReviewsByChargingStationId(id);

        Long i = 1L;
        for (SessionReviewDto review : reviews) {
            review.setId(i++);
            review.setReviewerImage("user" + i + ".png");
        }

        return reviews;
    }

    @GetMapping
    public List<ChargingStation> getAllChargingStations() {
        return chargingStationService.getAllChargingStations();
    }

    @PostMapping("/nearest")
    public List<ChargingStationResponseDto> findNearestChargingStations(
            @RequestBody NearestChargingStationsRequest request) {
        return chargingStationService.findNearestChargingStations(request.getLatitude(), request.getLongitude(),
                request.getDistanceThreshold());
    }

    @PostMapping("/enroute")
    public List<ChargingStationResponseDto> findEnrouteChargingStations(
            @RequestBody EnrouteChargingStationsRequest request) {
        return chargingStationService.findNearestChargingStations(18.5617769, 73.9446481, 10);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargingStationDto> getChargingStationById(@PathVariable Long id) {
        ChargingStationDto chargingStation = chargingStationService.getChargingStationById(id);

        if (chargingStation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(chargingStation);
    }

    @PostMapping
    public ChargingStation createChargingStation(@RequestBody ChargingStationDto chargingStation) {
        return chargingStationService.createChargingStation(chargingStation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChargingStation> updateChargingStation(@PathVariable Long id,
            @RequestBody ChargingStationDto chargingStationDetails) {
        Optional<ChargingStation> updatedChargingStation = chargingStationService.updateChargingStation(id,
                chargingStationDetails);
        return updatedChargingStation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChargingStation(@PathVariable Long id) {
        chargingStationService.deleteChargingStation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{tenantId}/generate-dummy-charging-stations")
    public boolean getChargingStationsByTenantId(@PathVariable Long tenantId) {
        chargingStationService.seedChargingStations(tenantId);
        return true;
    }

    @GetMapping("/favorites")
    public List<ChargingStationResponseDto> getFavoriteChargingStations(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return chargingStationService.getFavoriteChargingStations(currentUser.getId(), 18.5617769, 73.9446481);
    }

    @GetMapping("/favorites/{chargingStationId}")
    public boolean isFavorite(Authentication authentication,
            @PathVariable Long chargingStationId) {
        User currentUser = (User) authentication.getPrincipal();
        return chargingStationService.isFavorite(currentUser.getId(), chargingStationId);
    }

    @PostMapping("/favorites/{chargingStationId}")
    public ResponseEntity<Boolean> addChargingStationToFavorites(Authentication authentication,
            @PathVariable Long chargingStationId) {
        User currentUser = (User) authentication.getPrincipal();
        chargingStationService.addChargingStationToFavorites(currentUser.getId(), chargingStationId);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/favorites/{chargingStationId}")
    public ResponseEntity<Boolean> removeChargingStationFromFavorites(Authentication authentication,
            @PathVariable Long chargingStationId) {
        User currentUser = (User) authentication.getPrincipal();
        chargingStationService.removeChargingStationFromFavorites(currentUser.getId(), chargingStationId);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/{chargingStationId}/images")
    public ResponseEntity<String> uploadImages(
            @PathVariable Long chargingStationId,
            @RequestParam("files") List<MultipartFile> files) {
        try {
            var chargingStation = chargingStationService.getChargingStationEntity(chargingStationId);
            for (MultipartFile file : files) {
                imageService.saveImage(chargingStation, file);
            }
            return ResponseEntity.ok("Files uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload files");
        }
    }

    @GetMapping("/{chargingStationId}/images")
    public ResponseEntity<List<String>> getChargingStationImages(@PathVariable Long chargingStationId) {
        List<String> imageUrls = imageService.getChargingStationImages(chargingStationId);
        return ResponseEntity.ok(imageUrls);
    }
}
