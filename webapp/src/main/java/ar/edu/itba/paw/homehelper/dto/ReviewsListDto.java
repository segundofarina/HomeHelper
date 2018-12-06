package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.SProvider;
import org.springframework.context.MessageSource;

import java.util.*;

public class ReviewsListDto {
    private int page;
    private int pageSize;
    private int maxPage;
    private List<AptitudeReviewDto> reviews;

    public ReviewsListDto() {
    }

    public ReviewsListDto(List<Review> reviews, int page, int pageSize, int maxPage, Locale locale, MessageSource messageSource) {
        this.reviews = new LinkedList<>();

        Map<Integer, List<Review>> aptitudeReviewMap = new HashMap<>();

        for(Review review : reviews) {
            List<Review> reviewList;
            int aptitudeId = review.getAptitude().getId();

            if(!aptitudeReviewMap.containsKey(aptitudeId)) {
                reviewList = new ArrayList<>();
            } else {
                reviewList = aptitudeReviewMap.get(aptitudeId);
            }

            reviewList.add(review);
            aptitudeReviewMap.put(aptitudeId, reviewList);
        }

        for(Integer appointmentId : aptitudeReviewMap.keySet()) {
            this.reviews.add(new AptitudeReviewDto(appointmentId, aptitudeReviewMap.get(appointmentId)));
        }
    }
/*
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
*/

    public List<AptitudeReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<AptitudeReviewDto> reviews) {
        this.reviews = reviews;
    }
}
