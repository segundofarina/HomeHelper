package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Review;

import java.util.LinkedList;
import java.util.List;

public class ReviewsListDto {
    private List<ReviewDto> reviews;

    public ReviewsListDto() {
    }

    public ReviewsListDto(List<Review> reviews) {
        this.reviews = new LinkedList<>();
        for(Review review : reviews) {
            this.reviews.add(new ReviewDto(review));
        }
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }
}
