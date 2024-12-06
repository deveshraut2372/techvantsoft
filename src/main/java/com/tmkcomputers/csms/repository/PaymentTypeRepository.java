package com.tmkcomputers.csms.repository;

import com.tmkcomputers.csms.model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
}
