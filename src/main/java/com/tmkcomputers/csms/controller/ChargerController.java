package com.tmkcomputers.csms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tmkcomputers.csms.dto.ChargerDto;
import com.tmkcomputers.csms.model.Charger;
import com.tmkcomputers.csms.model.Connector;
import com.tmkcomputers.csms.service.ChargerService;
import com.tmkcomputers.csms.service.ConnectorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chargers")
public class ChargerController {

    @Autowired
    private ChargerService chargerService;

    @Autowired
    private ConnectorService connectorService;

    @GetMapping("/{id}/connectors")
    public List<Connector> getAllChargerConnectors(@PathVariable Long id) {
        return connectorService.getConnectorsByChargerId(id);
    }

    @GetMapping
    public List<Charger> getAllChargers() {
        return chargerService.getAllChargers();
    }

    @GetMapping("/charging-station/{chargingStationId}")
    public List<ChargerDto> getChargersByChargingStationId(@PathVariable Long chargingStationId) {
        return chargerService.getChargersByChargingStationId(chargingStationId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Charger> getChargerById(@PathVariable Long id) {
        Optional<Charger> charger = chargerService.getChargerById(id);
        return charger.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Charger createCharger(@RequestBody ChargerDto charger) {
        return chargerService.createCharger(charger);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Charger> updateCharger(@PathVariable Long id, @RequestBody ChargerDto chargerDetails) {
        Optional<Charger> updatedCharger = chargerService.updateCharger(id, chargerDetails);
        return updatedCharger.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharger(@PathVariable Long id) {
        chargerService.deleteCharger(id);
        return ResponseEntity.noContent().build();
    }
}
