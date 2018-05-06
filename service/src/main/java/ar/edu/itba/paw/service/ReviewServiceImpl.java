package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.ReviewDao;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.model.Review;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewDao reviewDao;

    @Override
    public List<Review> getReviewsOfAptitude(int aptitudeId) {
        return reviewDao.getReviewsOfAptitude(aptitudeId);
    }

    @Override
    public boolean insertReview(int userId, int aptitudeId, int rating, String comment) {
        return reviewDao.insertReview(userId,aptitudeId,rating,comment);
    }
}
