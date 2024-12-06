package com.tmkcomputers.csms.service;

import com.tmkcomputers.csms.model.OrganizationType;
import com.tmkcomputers.csms.repository.OrganizationTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class OrganizationTypeService {

    private final OrganizationTypeRepository repository;

    public OrganizationTypeService(OrganizationTypeRepository repository) {
        this.repository = repository;
    }

    public List<OrganizationType> findAll() {
        return repository.findAll();
    }

    public Optional<OrganizationType> findById(Long id) {
        return repository.findById(id);
    }

    public OrganizationType save(OrganizationType organizationType) {
        return repository.save(organizationType);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

