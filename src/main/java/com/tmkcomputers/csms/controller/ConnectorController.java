package com.tmkcomputers.csms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tmkcomputers.csms.dto.ConnectorDto;
import com.tmkcomputers.csms.model.Connector;
import com.tmkcomputers.csms.service.ConnectorService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/connectors")
public class ConnectorController {

    @Autowired
    private ConnectorService connectorService;
    @GetMapping
    public List<Connector> getAllConnectors() {
        return connectorService.getAllConnectors();
    }

    @GetMapping("/charger/{chargerId}")
    public List<Connector> getConnectorsByChargerId(@PathVariable Long chargerId) {
        return connectorService.getConnectorsByChargerId(chargerId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Connector> getConnectorById(@PathVariable Long id) {
        Optional<Connector> connector = connectorService.getConnectorById(id);
        return connector.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Connector createConnector(@RequestBody ConnectorDto connector) {
        return connectorService.createConnector(connector);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Connector> updateConnector(@PathVariable Long id, @RequestBody ConnectorDto connectorDetails) {
        Optional<Connector> updatedConnector = connectorService.updateConnector(id, connectorDetails);
        return updatedConnector.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConnector(@PathVariable Long id) {
        connectorService.deleteConnector(id);
        return ResponseEntity.noContent().build();
    }
}
