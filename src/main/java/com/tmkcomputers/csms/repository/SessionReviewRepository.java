package com.tmkcomputers.csms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tmkcomputers.csms.model.SessionReview;

@Repository
public interface SessionReviewRepository extends JpaRepository<SessionReview, Long> {
}
