package com.tmkcomputers.csms.controller;

import com.tmkcomputers.csms.dto.ChargerModelDto;
import com.tmkcomputers.csms.model.ChargerModel;
import com.tmkcomputers.csms.service.ChargerModelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/charger-models")
@Validated
public class ChargerModelController {

    private final ChargerModelService service;

    public ChargerModelController(ChargerModelService service) {
        this.service = service;
    }

    @GetMapping
    public List<ChargerModel> getAll() {
        return service.findAll();
    }

    @GetMapping("/charger-type/{chargerTypeId}/charger-oem/{chargerOemId}")
    public List<ChargerModel> getChargerModelsByChargerTypeAndChargerOem(
        @PathVariable Long chargerTypeId,
        @PathVariable Long chargerOemId) {
        
        return service.getChargerModelsByChargerTypeAndChargerOem(chargerTypeId, chargerOemId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargerModel> getById(@PathVariable Long id) {
        Optional<ChargerModel> chargerModel = service.findById(id);
        return chargerModel.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<ChargerModel> create(@Valid @RequestBody ChargerModelDto chargerModelDto) {
        ChargerModel savedChargerModel = service.createChargerModel(chargerModelDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedChargerModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChargerModel> update(@PathVariable Long id,
            @Valid @RequestBody ChargerModelDto chargerModelDto) {
        if (service.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Optional<ChargerModel> updatedChargerModel = service.updateChargerModel(id, chargerModelDto);
        return updatedChargerModel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
