package com.tmkcomputers.csms.service;

import org.springframework.stereotype.Service;

import com.tmkcomputers.csms.model.ChargerOem;
import com.tmkcomputers.csms.repository.ChargerOemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ChargerOemService {

    private final ChargerOemRepository repository;

    public ChargerOemService(ChargerOemRepository repository) {
        this.repository = repository;
    }

    public List<ChargerOem> findAll() {
        return repository.findAll();
    }

    public Optional<ChargerOem> findById(Long id) {
        return repository.findById(id);
    }

    public ChargerOem save(ChargerOem ChargerOem) {
        return repository.save(ChargerOem);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

