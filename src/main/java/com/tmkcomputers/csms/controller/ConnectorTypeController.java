package com.tmkcomputers.csms.controller;

import com.tmkcomputers.csms.model.ConnectorType;
import com.tmkcomputers.csms.service.ConnectorTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/connector-types")
@Validated
public class ConnectorTypeController {

    private final ConnectorTypeService service;

    public ConnectorTypeController(ConnectorTypeService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public void handleOptions() {
        // This method is intentionally left empty to allow OPTIONS requests
    }

    @GetMapping
    public List<ConnectorType> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConnectorType> getById(@PathVariable Long id) {
        Optional<ConnectorType> connectorType = service.findById(id);
        return connectorType.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<ConnectorType> create(@Valid @RequestBody ConnectorType connectorType) {
        ConnectorType savedConnectorType = service.save(connectorType);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedConnectorType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConnectorType> update(@PathVariable Long id, @Valid @RequestBody ConnectorType connectorType) {
        if (service.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        connectorType.setId(id);
        ConnectorType updatedConnectorType = service.save(connectorType);
        return ResponseEntity.ok(updatedConnectorType);
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


