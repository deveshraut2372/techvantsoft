package com.tmkcomputers.csms.repository;

import com.tmkcomputers.csms.model.OperationalHour;

import org.springframework.data.jpa.repository.JpaRepository;


public interface OperationalHourRepository extends JpaRepository<OperationalHour, Long> {
    
}
