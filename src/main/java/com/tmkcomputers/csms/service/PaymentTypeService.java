package com.tmkcomputers.csms.service;

import com.tmkcomputers.csms.model.PaymentType;
import com.tmkcomputers.csms.repository.PaymentTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class PaymentTypeService {

    private final PaymentTypeRepository repository;

    public PaymentTypeService(PaymentTypeRepository repository) {
        this.repository = repository;
    }

    public List<PaymentType> findAll() {
        return repository.findAll();
    }

    public Optional<PaymentType> findById(Long id) {
        return repository.findById(id);
    }

    public PaymentType save(PaymentType paymentType) {
        return repository.save(paymentType);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

