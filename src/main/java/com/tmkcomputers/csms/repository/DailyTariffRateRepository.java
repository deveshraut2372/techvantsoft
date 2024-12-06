package com.tmkcomputers.csms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tmkcomputers.csms.model.DailyTariffRate;

public interface DailyTariffRateRepository extends JpaRepository<DailyTariffRate, Long> {
}
