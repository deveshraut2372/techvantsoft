package com.tmkcomputers.csms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.tmkcomputers.csms.model.DailyTariffRate;
import com.tmkcomputers.csms.service.DailyTariffRateService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/daily-tariff-rates")
@Validated
public class DailyTariffRateController {

    private final DailyTariffRateService service;

    public DailyTariffRateController(DailyTariffRateService service) {
        this.service = service;
    }

    @GetMapping
    public List<DailyTariffRate> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DailyTariffRate> getById(@PathVariable Long id) {
        Optional<DailyTariffRate> dailyTariffRate = service.findById(id);
        return dailyTariffRate.map(ResponseEntity::ok)
                              .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<DailyTariffRate> create(@Valid @RequestBody DailyTariffRate dailyTariffRate) {
        DailyTariffRate savedDailyTariffRate = service.save(dailyTariffRate);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDailyTariffRate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DailyTariffRate> update(@PathVariable Long id, @Valid @RequestBody DailyTariffRate dailyTariffRate) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        dailyTariffRate.setId(id);
        DailyTariffRate updatedDailyTariffRate = service.save(dailyTariffRate);
        return ResponseEntity.ok(updatedDailyTariffRate);
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

