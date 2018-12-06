package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.ReviewDao;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewDao reviewDao;

    @Override
    public Review getReview(int id) {
        return reviewDao.getReview(id);
    }
}
