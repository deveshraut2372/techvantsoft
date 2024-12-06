package com.tmkcomputers.csms.service;

import org.springframework.stereotype.Service;

import com.tmkcomputers.csms.model.DailyTariffRate;
import com.tmkcomputers.csms.repository.DailyTariffRateRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DailyTariffRateService {

    private final DailyTariffRateRepository repository;

    public DailyTariffRateService(DailyTariffRateRepository repository) {
        this.repository = repository;
    }

    public List<DailyTariffRate> findAll() {
        return repository.findAll();
    }

    public Optional<DailyTariffRate> findById(Long id) {
        return repository.findById(id);
    }

    public DailyTariffRate save(DailyTariffRate dailyTariffRate) {
        return repository.save(dailyTariffRate);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

