package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Review;
import org.springframework.context.MessageSource;

import java.util.*;

public class ReviewsListDto {
    private List<AptitudeReviewDto> reviews;

    public ReviewsListDto() {
    }

    public ReviewsListDto(List<Review> reviews, Locale locale, MessageSource messageSource) {
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

    public List<AptitudeReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<AptitudeReviewDto> reviews) {
        this.reviews = reviews;
    }
}
