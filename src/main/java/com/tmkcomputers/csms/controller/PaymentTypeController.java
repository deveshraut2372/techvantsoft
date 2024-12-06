package com.tmkcomputers.csms.controller;

import com.tmkcomputers.csms.model.PaymentType;
import com.tmkcomputers.csms.service.PaymentTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment-types")
@Validated
public class PaymentTypeController {

    private final PaymentTypeService service;

    public PaymentTypeController(PaymentTypeService service) {
        this.service = service;
    }

    @GetMapping
    public List<PaymentType> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentType> getById(@PathVariable Long id) {
        Optional<PaymentType> paymentType = service.findById(id);
        return paymentType.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<PaymentType> create(@Valid @RequestBody PaymentType paymentType) {
        PaymentType savedPaymentType = service.save(paymentType);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPaymentType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentType> update(@PathVariable Long id, @Valid @RequestBody PaymentType paymentType) {
        if (service.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        paymentType.setId(id);
        PaymentType updatedPaymentType = service.save(paymentType);
        return ResponseEntity.ok(updatedPaymentType);
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


