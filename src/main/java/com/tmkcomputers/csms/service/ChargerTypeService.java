package com.tmkcomputers.csms.service;

import com.tmkcomputers.csms.model.ChargerType;
import com.tmkcomputers.csms.repository.ChargerTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ChargerTypeService {

    private final ChargerTypeRepository repository;

    public ChargerTypeService(ChargerTypeRepository repository) {
        this.repository = repository;
    }

    public List<ChargerType> findAll() {
        return repository.findAll();
    }

    public Optional<ChargerType> findById(Long id) {
        return repository.findById(id);
    }

    public ChargerType save(ChargerType chargerType) {
        return repository.save(chargerType);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

