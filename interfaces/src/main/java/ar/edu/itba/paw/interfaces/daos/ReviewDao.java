package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.Review;

import java.util.List;

public interface ReviewDao {

    List<Review> getReviewsOfAptitude(int aptitudeId);

    boolean insertReview(int userId, int aptitudeId, int quality,int cleanness, int price, int punctuality, int treatment, String comment);

    boolean removeReviewsOfAptitude(int aptId);
}
