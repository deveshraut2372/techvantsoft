package com.tmkcomputers.csms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tmkcomputers.csms.model.SessionReview;
import com.tmkcomputers.csms.service.SessionReviewService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/session-reviews")
public class SessionReviewController {

    @Autowired
    private SessionReviewService sessionReviewService;

    @GetMapping
    public List<SessionReview> getAllSessionReviews() {
        return sessionReviewService.getAllSessionReviews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionReview> getSessionReviewById(@PathVariable Long id) {
        Optional<SessionReview> sessionReview = sessionReviewService.getSessionReviewById(id);
        return sessionReview.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public SessionReview createSessionReview(@RequestBody SessionReview sessionReview) {
        return sessionReviewService.createSessionReview(sessionReview);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessionReview> updateSessionReview(
            @PathVariable Long id,
            @RequestBody SessionReview sessionReview) {
        SessionReview updatedReview = sessionReviewService.updateSessionReview(id, sessionReview);
        if (updatedReview != null) {
            return ResponseEntity.ok(updatedReview);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSessionReview(@PathVariable Long id) {
        sessionReviewService.deleteSessionReview(id);
        return ResponseEntity.noContent().build();
    }
}
