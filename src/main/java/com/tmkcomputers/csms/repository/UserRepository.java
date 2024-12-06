package com.tmkcomputers.csms.repository;

import com.tmkcomputers.csms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String email);
    Optional<User> findByMobileNumber(String mobileNumber);
}
