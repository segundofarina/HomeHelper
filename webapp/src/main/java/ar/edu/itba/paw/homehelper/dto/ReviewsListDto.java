package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.SProvider;
import org.springframework.context.MessageSource;

import java.util.*;

public class ReviewsListDto {
    private List<ReviewDto> reviews;
    private int page;
    private int pageSize;
    private int maxPage;

    public ReviewsListDto() {
    }

    public ReviewsListDto(List<Review> reviews, Locale locale, MessageSource messageSource) {
        this.reviews = new LinkedList<>();
        for(Review review : reviews) {
            this.reviews.add(new ReviewDto(review,locale,messageSource));
        }
    }

    public ReviewsListDto(List<Review> reviews, int page, int pageSize, int maxPage, Locale locale, MessageSource messageSource) {
        this.page = page;
        this.pageSize = pageSize;
        this.maxPage = maxPage;

        this.reviews = new ArrayList<>();
        for(Review review : reviews) {
            this.reviews.add(new ReviewDto(review,locale,messageSource));
        }

        reviews.sort(new Comparator<Review>() {
            @Override
            public int compare(Review o1, Review o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }
}
