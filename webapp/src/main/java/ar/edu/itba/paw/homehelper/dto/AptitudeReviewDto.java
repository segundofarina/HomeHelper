package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Review;

import java.util.List;
import java.util.stream.Collectors;

public class AptitudeReviewDto {
    private int aptitudeId;
    private List<ReviewDto> reviews;

    public AptitudeReviewDto() {
    }

    public AptitudeReviewDto(int id, List<Review> reviews) {
        this.aptitudeId = id;
        this.reviews = reviews.stream().map(review -> new ReviewDto(review)).collect(Collectors.toList());
    }

    public int getAptitudeId() {
        return aptitudeId;
    }

    public void setAptitudeId(int aptitudeId) {
        this.aptitudeId = aptitudeId;
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }
}
