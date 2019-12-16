package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.ReviewDao;
import ar.edu.itba.paw.model.Review;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class ReviewHibernateDao implements ReviewDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Review> getReviewsOfAptitude(int aptitudeId) {
        final TypedQuery<Review> query = em.createQuery("from Review as r where r.aptitude.id = :aptitudeid", Review.class);
        query.setParameter("aptitudeid", aptitudeId);
        return query.getResultList();
    }

    @Override
    public Review getReview(int reviewId) {
        return em.find(Review.class,reviewId);
    }

}
