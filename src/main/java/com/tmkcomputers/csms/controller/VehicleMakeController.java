package com.tmkcomputers.csms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.tmkcomputers.csms.model.VehicleMake;
import com.tmkcomputers.csms.service.VehicleMakeService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicle-makes")
@Validated
public class VehicleMakeController {

    private final VehicleMakeService service;

    public VehicleMakeController(VehicleMakeService service) {
        this.service = service;
    }

    @GetMapping
    public List<VehicleMake> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleMake> getById(@PathVariable Long id) {
        Optional<VehicleMake> vehicleMake = service.findById(id);
        return vehicleMake.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<VehicleMake> create(@Valid @RequestBody VehicleMake vehicleMake) {
        VehicleMake savedOem = service.save(vehicleMake);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleMake> update(@PathVariable Long id, @Valid @RequestBody VehicleMake vehicleMake) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        vehicleMake.setId(id);
        VehicleMake updatedOem = service.save(vehicleMake);
        return ResponseEntity.ok(updatedOem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
