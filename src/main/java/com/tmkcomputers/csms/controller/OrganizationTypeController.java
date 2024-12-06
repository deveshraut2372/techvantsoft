package com.tmkcomputers.csms.controller;

import com.tmkcomputers.csms.model.OrganizationType;
import com.tmkcomputers.csms.service.OrganizationTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/organization-types")
@Validated
public class OrganizationTypeController {

    private final OrganizationTypeService service;

    public OrganizationTypeController(OrganizationTypeService service) {
        this.service = service;
    }

    @GetMapping
    public List<OrganizationType> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationType> getById(@PathVariable Long id) {
        Optional<OrganizationType> organizationType = service.findById(id);
        return organizationType.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<OrganizationType> create(@Valid @RequestBody OrganizationType organizationType) {
        OrganizationType savedOrganizationType = service.save(organizationType);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrganizationType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganizationType> update(@PathVariable Long id, @Valid @RequestBody OrganizationType organizationType) {
        if (service.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        organizationType.setId(id);
        OrganizationType updatedOrganizationType = service.save(organizationType);
        return ResponseEntity.ok(updatedOrganizationType);
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


