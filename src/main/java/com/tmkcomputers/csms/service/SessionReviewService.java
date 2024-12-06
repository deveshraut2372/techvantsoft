package com.tmkcomputers.csms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmkcomputers.csms.model.SessionReview;
import com.tmkcomputers.csms.repository.SessionReviewRepository;

@Service
public class SessionReviewService {

    @Autowired
    private SessionReviewRepository sessionReviewRepository;

    public List<SessionReview> getAllSessionReviews() {
        return sessionReviewRepository.findAll();
    }

    public Optional<SessionReview> getSessionReviewById(Long id) {
        return sessionReviewRepository.findById(id);
    }

    public SessionReview createSessionReview(SessionReview sessionReview) {
        return sessionReviewRepository.save(sessionReview);
    }

    public SessionReview updateSessionReview(Long id, SessionReview sessionReview) {
        Optional<SessionReview> existingReview = sessionReviewRepository.findById(id);
        if (existingReview.isPresent()) {
            sessionReview.setId(id);
            return sessionReviewRepository.save(sessionReview);
        }
        return null;
    }

    public void deleteSessionReview(Long id) {
        sessionReviewRepository.deleteById(id);
    }
}
