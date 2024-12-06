package com.tmkcomputers.csms.controller;

import com.tmkcomputers.csms.model.ChargerType;
import com.tmkcomputers.csms.service.ChargerTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/charger-types")
@Validated
public class ChargerTypeController {

    private final ChargerTypeService service;

    public ChargerTypeController(ChargerTypeService service) {
        this.service = service;
    }

    @GetMapping
    public List<ChargerType> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargerType> getById(@PathVariable Long id) {
        Optional<ChargerType> chargerType = service.findById(id);
        return chargerType.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<ChargerType> create(@Valid @RequestBody ChargerType chargerType) {
        ChargerType savedChargerType = service.save(chargerType);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedChargerType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChargerType> update(@PathVariable Long id, @Valid @RequestBody ChargerType chargerType) {
        if (service.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        chargerType.setId(id);
        ChargerType updatedChargerType = service.save(chargerType);
        return ResponseEntity.ok(updatedChargerType);
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


