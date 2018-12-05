package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Review;
import org.springframework.context.MessageSource;

import java.util.*;

public class ReviewsListDto {
    private List<AppointmentReviewDto> reviews;

    public ReviewsListDto() {
    }

    public ReviewsListDto(List<Review> reviews, Locale locale, MessageSource messageSource) {
        this.reviews = new LinkedList<>();

        Map<Integer, List<Review>> appointmentReviewMap = new HashMap<>();

        for(Review review : reviews) {
            List<Review> reviewList = null;
            int aptitudeId = review.getAptitude().getId();

            if(!appointmentReviewMap.containsKey(aptitudeId)) {
                reviewList = new ArrayList<>();
            } else {
                reviewList = appointmentReviewMap.get(aptitudeId);
            }

            reviewList.add(review);
            appointmentReviewMap.put(aptitudeId, reviewList);

            for(Integer appointmentId : appointmentReviewMap.keySet()) {
                this.reviews.add(new AppointmentReviewDto(appointmentId, appointmentReviewMap.get(appointmentId)));
            }
        }
    }

    public List<AppointmentReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<AppointmentReviewDto> reviews) {
        this.reviews = reviews;
    }
}
