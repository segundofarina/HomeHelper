package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getReviewsOfAptitude(int aptitudeId);

    boolean insertReview(int userId, int aptitudeId, int rating, String comment );
}
