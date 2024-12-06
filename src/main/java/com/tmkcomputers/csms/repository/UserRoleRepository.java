package com.tmkcomputers.csms.repository;

import com.tmkcomputers.csms.model.User;
import com.tmkcomputers.csms.model.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    Optional<UserRole> findByUser(User user);

    List<UserRole> findByEntityTypeAndEntityId(String string, long l);
}