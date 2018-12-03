package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Review;
import org.springframework.context.MessageSource;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class ReviewsListDto {
    private List<ReviewDto> reviews;

    public ReviewsListDto() {
    }

    public ReviewsListDto(List<Review> reviews, Locale locale, MessageSource messageSource) {
        this.reviews = new LinkedList<>();
        for(Review review : reviews) {
            this.reviews.add(new ReviewDto(review,locale,messageSource));
        }
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }
}
