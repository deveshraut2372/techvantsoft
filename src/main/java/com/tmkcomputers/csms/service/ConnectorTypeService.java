package com.tmkcomputers.csms.service;

import com.tmkcomputers.csms.model.ConnectorType;
import com.tmkcomputers.csms.repository.ConnectorTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ConnectorTypeService {

    private final ConnectorTypeRepository repository;

    public ConnectorTypeService(ConnectorTypeRepository repository) {
        this.repository = repository;
    }

    public List<ConnectorType> findAll() {
        return repository.findAll();
    }

    public Optional<ConnectorType> findById(Long id) {
        return repository.findById(id);
    }

    public ConnectorType save(ConnectorType connectorType) {
        return repository.save(connectorType);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

