package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Review;

import java.util.List;

public interface ReviewDao {

    List<Review> getReviewsBy(int aptitudeId);

    boolean insertReview(int userId, int aptitudeId, int rating, String comment );

}
