package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.Review;

import java.util.List;

public interface ReviewDao {

    List<Review> getReviewsOfAptitude(int aptitudeId);

    Review getReview(int reviewId);
}
