package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.interfaces.daos.ReviewDao;
import ar.edu.itba.paw.interfaces.daos.SProviderDao;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
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

    @Autowired
    SProviderDao sProviderDao;

    @Before
    public void setUp() {

    }

    @Test
    public void getReviewsOfAptitudeTest(){

        assertEquals(2,reviewDao.getReviewsOfAptitude(Const.VALID_APTITUDE_ID).size());

        assertEquals(1,reviewDao.getReviewsOfAptitude(Const.VALID_APTITUDE2_ID).size());

        assertEquals(null,reviewDao.getReviewsOfAptitude(Const.INVALID_APTITUDE_ID));
    }

    @Test
    public void insertReviewTest(){

//        assertTrue(reviewDao.insertReview(Const.USER3_ID,Const.VALID_APTITUDE_ID,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_COMMENT));
//
//        assertFalse(reviewDao.insertReview(Const.INVALIDAD_USER_ID,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,5,Const.VALID_COMMENT));
//
//        assertFalse(reviewDao.insertReview(Const.USER3_ID,Const.INVALID_APTITUDE_ID,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_COMMENT));
//
//        assertFalse(reviewDao.insertReview(Const.USER3_ID,Const.VALID_APTITUDE_ID,Const.INVALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_COMMENT));
//
//        assertFalse(reviewDao.insertReview(Const.USER3_ID,Const.VALID_APTITUDE_ID,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.INVALID_COMMENT));

    }

//    @Test
//    public void removeReviewsOfAptitudeTest(){
//
//        assertTrue(reviewDao.removeReviewsOfAptitude(Const.VALID_APTITUDE_ID));
//
//        assertFalse(reviewDao.removeReviewsOfAptitude(Const.INVALID_APTITUDE_ID));
//
//        assertFalse(reviewDao.removeReviewsOfAptitude(Const.VALID_APTITUDE4_ID));
//    }
}
