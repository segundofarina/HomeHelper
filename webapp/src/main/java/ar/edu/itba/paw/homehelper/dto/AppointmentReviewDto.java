package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Review;

import java.util.List;
import java.util.stream.Collectors;

public class AppointmentReviewDto {
    private int appointmentId;
    private List<ReviewDto> reviews;

    public AppointmentReviewDto() {
    }

    public AppointmentReviewDto(int id, List<Review> reviews) {
        this.appointmentId = id;
        this.reviews = reviews.stream().map(review -> new ReviewDto(review)).collect(Collectors.toList());
    }

    public int getId() {
        return appointmentId;
    }

    public void setId(int id) {
        this.appointmentId = id;
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }
}
