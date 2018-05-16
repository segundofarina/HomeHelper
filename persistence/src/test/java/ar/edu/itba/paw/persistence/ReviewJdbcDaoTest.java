package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.interfaces.daos.ReviewDao;
import ar.edu.itba.paw.interfaces.daos.SProviderDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

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

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void getReviewsOfAptitudeTest(){

        assertEquals(2,reviewDao.getReviewsOfAptitude(Const.VALID_APTITUDE_ID).size());

        assertEquals(1,reviewDao.getReviewsOfAptitude(Const.VALID_APTITUDE2_ID).size());

        assertEquals(0,reviewDao.getReviewsOfAptitude(Const.INVALID_APTITUDE_ID).size());
    }

    @Test
    public void insertReviewTest(){

        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "reviews");

        reviewDao.insertReview(Const.USER3_ID,Const.VALID_APTITUDE_ID,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_COMMENT);

        assertEquals(++count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "reviews"));

        try {
            reviewDao.insertReview(Const.INVALIDAD_USER_ID, Const.VALID_CALIFICATION, Const.VALID_CALIFICATION, Const.VALID_CALIFICATION, Const.VALID_CALIFICATION, Const.VALID_CALIFICATION, Const.VALID_CALIFICATION, Const.VALID_COMMENT);
        }catch (Exception e){
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "reviews"));
        }

        try{
            reviewDao.insertReview(Const.USER3_ID,Const.INVALID_APTITUDE_ID,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_COMMENT);
        }catch (Exception e){
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "reviews"));
        }

        try{
            reviewDao.insertReview(Const.USER3_ID,Const.VALID_APTITUDE_ID,Const.INVALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_COMMENT);
        }catch (Exception e){
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "reviews"));
        }
        try{
            reviewDao.insertReview(Const.USER3_ID,Const.VALID_APTITUDE_ID,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.VALID_CALIFICATION,Const.INVALID_COMMENT);
        }catch (Exception e){
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "reviews"));
        }

    }

    @Test
    public void removeReviewsOfAptitudeTest(){

        assertTrue(reviewDao.removeReviewsOfAptitude(Const.VALID_APTITUDE_ID));

        assertFalse(reviewDao.removeReviewsOfAptitude(Const.INVALID_APTITUDE_ID));

        assertFalse(reviewDao.removeReviewsOfAptitude(Const.VALID_APTITUDE4_ID));
    }
}
