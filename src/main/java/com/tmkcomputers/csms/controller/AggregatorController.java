package com.tmkcomputers.csms.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tmkcomputers.csms.dto.ChargingStationResponseDto;
import com.tmkcomputers.csms.service.AggregatorService;
import com.tmkcomputers.csms.service.ChargingStationService;
import com.tmkcomputers.csms.utlity.DistanceCalculator;

import java.util.List;

@RestController
@RequestMapping("/api/aggregator")
public class AggregatorController {

    @Autowired
    private AggregatorService aggregatorService;

    @Autowired
    private ChargingStationService chargingStationService;

    // Endpoint 1: Get all nearby charging stations with filters and pagination
    // http://localhost:8080/api/aggregator/nearby?latitude=34.0522&longitude=-118.2437&radius=50&connectionTypeId=1&levelId=2&statusTypeId=50&usageTypeId=1&minPowerKw=50&maxResults=10&offset=0
    @GetMapping("/nearby")
    public ResponseEntity<?> getNearbyStations(@RequestParam double latitude,
                                               @RequestParam double longitude,
                                               @RequestParam int radius,
                                               @RequestParam(required = false) Integer[] connectionTypeId,  // Allow multiple connection types
                                               @RequestParam(required = false) Integer levelId,
                                               @RequestParam(required = false) Integer statusTypeId,
                                               @RequestParam(required = false) Integer usageTypeId,
                                               @RequestParam(required = false) Integer minPowerKw,
                                               @RequestParam(defaultValue = "10") int maxResults,
                                               @RequestParam(defaultValue = "0") int offset) {
        try {
            JSONArray stations = aggregatorService.getNearbyChargingStations(latitude, longitude, radius, connectionTypeId, levelId, statusTypeId, usageTypeId, minPowerKw, maxResults, offset);
            // Use the mapper to convert JSONArray to List of DTOs
            List<ChargingStationResponseDto> stationDTOs = chargingStationService.parseJsonData(latitude, longitude, radius, stations);

            return ResponseEntity.ok(stationDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving charging stations: " + e.getMessage());
        }
    }

    // Endpoint 2: Get details of a specific charging station by ID
    // http://localhost:8080/api/aggregator/station/139728
    @GetMapping("/station/{id}")
    public ResponseEntity<?> getStationDetails(@PathVariable int id) {
        try {
            JSONObject station = aggregatorService.getChargingStationDetails(id);
            return ResponseEntity.ok(station.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error retrieving station details: " + e.getMessage());
        }
    }

    @GetMapping("/enroute")
    public ResponseEntity<?> getChargingStationsBetweenPlaces(
            @RequestParam double lat1,
            @RequestParam double lon1,
            @RequestParam double lat2,
            @RequestParam double lon2,
            @RequestParam(defaultValue = "50") double radius,
            @RequestParam(required = false) Integer[] connectionTypeId, // Allow multiple connection types
            @RequestParam(required = false) Integer levelId,
            @RequestParam(required = false) Integer statusTypeId,
            @RequestParam(required = false) Integer usageTypeId,
            @RequestParam(required = false) Integer minPowerKw,
            @RequestParam(defaultValue = "10") int maxResults,
            @RequestParam(defaultValue = "0") int offset) {

        double totalDistance = DistanceCalculator.calculateDistance(lat1, lon1, lat2, lon2);
        double[] midpoint = DistanceCalculator.calculateMidpoint(lat1, lon1, lat2, lon2);

        int finalRadius = (int) (totalDistance > radius ? totalDistance : radius);

        try {
            JSONArray stations = aggregatorService.getNearbyChargingStations(midpoint[0], midpoint[1], finalRadius,
                    connectionTypeId, levelId, statusTypeId, usageTypeId, minPowerKw, maxResults, offset);
            // Use the mapper to convert JSONArray to List of DTOs
            List<ChargingStationResponseDto> stationDTOs = chargingStationService.parseJsonData(lat1, lon1,
                    finalRadius, stations);

            return ResponseEntity.ok(stationDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving charging stations: " + e.getMessage());
        }
    }
}
