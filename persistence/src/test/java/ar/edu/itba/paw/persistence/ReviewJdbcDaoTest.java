package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.interfaces.daos.ReviewDao;
import ar.edu.itba.paw.model.Review;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)

public class ReviewJdbcDaoTest {

    @Autowired
    ReviewDao reviewDao;

    @Before
    public void setUp() {

    }

    @Test
    public void getReviewsOfAptitudeTest(){
        List<Review> martinReviews = reviewDao.getReviewsOfAptitude(1);
        assertEquals(2,martinReviews.size());

        martinReviews = reviewDao.getReviewsOfAptitude(2);
        assertEquals(1,martinReviews.size());
    }

    @Test
    public void insertReviewTest(){

        assertTrue(reviewDao.insertReview(3,1,1,"No me gusto tu trabajo cabroon"));

        assertFalse(reviewDao.insertReview(40,1,1,"No me gusto tu trabajo cabroon"));

        assertFalse(reviewDao.insertReview(3,40,1,"No me gusto tu trabajo cabroon"));

    }
}
