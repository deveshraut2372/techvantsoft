package com.tmkcomputers.csms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmkcomputers.csms.dto.TenantDto;
import com.tmkcomputers.csms.model.Tenant;
import com.tmkcomputers.csms.model.ChargingStation;
import com.tmkcomputers.csms.service.TenantService;
import com.tmkcomputers.csms.service.ChargingStationService;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private TenantService service;
    private ChargingStationService chargingStationService;
    
    public TenantController(TenantService service, ChargingStationService chargingStationService) {
        this.service = service;
        this.chargingStationService = chargingStationService;
    }

    @GetMapping("/{id}/charging-stations")
    public List<ChargingStation> getAllTenantChargingStations(@PathVariable Long id) {
        return chargingStationService.getChargingStationsByTenantId(id);
    }

    @GetMapping
    public List<Tenant> getAllTenants() {
        return service.getAllTenants();
    }

    @GetMapping("/stats")
    public List<TenantDto> findTenantChargingStationChargerStats() {
        return service.findTenantChargingStationChargerStats();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantDto> getTenantById(@PathVariable Long id) {
        Optional<TenantDto> tenant = service.getTenantById(id);
        return tenant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Tenant createTenant(@RequestBody Tenant tenant) {
        return service.createTenant(tenant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tenant> updateTenant(@PathVariable Long id, @RequestBody Tenant tenantDetails) {
        Optional<Tenant> updatedTenant = service.updateTenant(id, tenantDetails);
        return updatedTenant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenant(@PathVariable Long id) {
        service.deleteTenant(id);
        return ResponseEntity.noContent().build();
    }
}
