package com.tmkcomputers.csms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionReviewDto {

    public SessionReviewDto(String reviewerName, double rating, String review) {
        this.reviewerName = reviewerName;
        this.rating = rating;
        this.review = review;
    }

    private Long id;
    private String reviewerImage;
    private String reviewerName;
    private double rating;
    private String review;
}
