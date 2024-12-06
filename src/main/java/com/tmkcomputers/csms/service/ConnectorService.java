package com.tmkcomputers.csms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmkcomputers.csms.dto.ConnectorDto;
import com.tmkcomputers.csms.exception.ResourceNotFoundException;
import com.tmkcomputers.csms.model.Charger;
import com.tmkcomputers.csms.model.Connector;
import com.tmkcomputers.csms.model.ConnectorType;
import com.tmkcomputers.csms.repository.ChargerRepository;
import com.tmkcomputers.csms.repository.ConnectorRepository;
import com.tmkcomputers.csms.repository.ConnectorTypeRepository;

@Service
public class ConnectorService {

    @Autowired
    private ConnectorRepository connectorRepository;

    @Autowired
    private ChargerRepository chargerRepository;

    @Autowired
    private ConnectorTypeRepository connectorTypeRepository;

    public List<Connector> getAllConnectors() {
        return connectorRepository.findAll();
    }

    public List<Connector> getConnectorsByChargerId(Long chargerId) {
        return connectorRepository.findByChargerId(chargerId);
    }

    public Optional<Connector> getConnectorById(Long id) {
        return connectorRepository.findById(id);
    }

    public Connector createConnector(ConnectorDto connectorDto) {
        Charger charger = chargerRepository.findById(connectorDto.getChargerId())
            .orElseThrow(() -> {
                throw new ResourceNotFoundException("Charger", "id", connectorDto.getChargerId());
            });

        ConnectorType connectorType = connectorTypeRepository.findById(connectorDto.getConnectorTypeId())
            .orElseThrow(() -> {
                throw new ResourceNotFoundException("ConnectorType", "id", connectorDto.getConnectorTypeId());
            });

        Connector newConnector = new Connector();
        newConnector.setName(connectorDto.getName());
        newConnector.setDescription(connectorDto.getDescription());
        newConnector.setCharger(charger);
        newConnector.setConnectorType(connectorType);

        return connectorRepository.save(newConnector);
    }

    public Optional<Connector> updateConnector(Long id, ConnectorDto connectorDetails) {
        return connectorRepository.findById(id)
                .map(connector -> {

                    if (connectorDetails.getChargerId() != connector.getCharger().getId()) {
                        chargerRepository.findById(connectorDetails.getChargerId()).ifPresent(connector::setCharger);
                    }

                    if (connectorDetails.getConnectorTypeId() != connector.getConnectorType().getId()) {
                        connectorTypeRepository.findById(connectorDetails.getConnectorTypeId()).ifPresent(connector::setConnectorType);
                    }

                    connector.setName(connectorDetails.getName());
                    connector.setDescription(connectorDetails.getDescription());
                    return connectorRepository.save(connector);
                });
    }

    public void deleteConnector(Long id) {
        connectorRepository.deleteById(id);
    }
}
