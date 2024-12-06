package com.tmkcomputers.csms.service;

import org.springframework.stereotype.Service;

import com.tmkcomputers.csms.model.VehicleMake;
import com.tmkcomputers.csms.repository.VehicleMakeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleMakeService {

    private final VehicleMakeRepository repository;

    public VehicleMakeService(VehicleMakeRepository repository) {
        this.repository = repository;
    }

    public List<VehicleMake> findAll() {
        return repository.findAll();
    }

    public Optional<VehicleMake> findById(Long id) {
        return repository.findById(id);
    }

    public VehicleMake save(VehicleMake VehicleMake) {
        return repository.save(VehicleMake);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

