package com.tmkcomputers.csms.controller;

import com.tmkcomputers.csms.dto.VehicleModelDto;
import com.tmkcomputers.csms.model.VehicleModel;
import com.tmkcomputers.csms.service.VehicleModelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicle-models")
@Validated
public class VehicleModelController {

    private final VehicleModelService service;

    public VehicleModelController(VehicleModelService service) {
        this.service = service;
    }

    @GetMapping
    public List<VehicleModel> getAll() {
        return service.findAll();
    }

    @GetMapping("/vehicle-make/{vehicleMakeId}")
    public List<VehicleModel> getVehicleModelsByVehicleMake(@PathVariable Long vehicleMakeId) {
        
        return service.getVehicleModelsByVehicleMake(vehicleMakeId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleModel> getById(@PathVariable Long id) {
        Optional<VehicleModel> vehicleModel = service.findById(id);
        return vehicleModel.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<VehicleModel> create(@Valid @RequestBody VehicleModelDto vehicleModelDto) {
        VehicleModel savedVehicleModel = service.createVehicleModel(vehicleModelDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicleModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleModel> update(@PathVariable Long id,
            @Valid @RequestBody VehicleModelDto vehicleModelDto) {
        if (service.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Optional<VehicleModel> updatedVehicleModel = service.updateVehicleModel(id, vehicleModelDto);
        return updatedVehicleModel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
