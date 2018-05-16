package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {

    boolean insertReview(int appointmentId, int quality, int cleanness, int price, int punctuality, int treatment, String comment);

    Optional<Review> getReview(int appointmentId);

    List<Review> getReviewsOfAptitude(int aptId);

    boolean removeReview(int appointmentId);
}
