package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.AptitudeDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.model.Aptitude;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional

public class AptitudeHibernateDaoTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private AptitudeDao aptitudeDao;

    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;


    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }
    @Test
    public void getAptitudesOfUserTest(){

        assertEquals(Const.VALID_APTITUDE_ID,aptitudeDao.getAptitudesOfUser(Const.USER3_ID).get(0).getId());

        assertNotEquals(Const.SERVICETYPE2_ID,aptitudeDao.getAptitudesOfUser(Const.USER3_ID).get(0).getId());

        assertEquals(0,aptitudeDao.getAptitudesOfUser(Const.INVALIDAD_USER_ID).size());

    }

    @Test
    public void insertAptitudeTest(){

        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes");

        aptitudeDao.insertAptitude(Const.SPROVIDER_ID,Const.SERVICETYPE3_ID,Const.VALID_DESCRIPTION);
        em.flush();
        assertEquals(++count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes"));

        try {
            aptitudeDao.insertAptitude(Const.INVALID_SERVICE_PROVIDER_ID, Const.SERVICETYPE3_ID, Const.VALID_DESCRIPTION);

        }catch (Exception e) {
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes"));
        }

       try{
            aptitudeDao.insertAptitude(Const.SPROVIDER_ID, Const.INVALID_SERVICE_TYPE_ID, Const.VALID_DESCRIPTION);

       }catch (Exception e) {
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes"));
       }

        try{
            aptitudeDao.insertAptitude(Const.SPROVIDER_ID, Const.SERVICETYPE2_ID, Const.INVALID_DESCRIPTION);

        }catch (Exception e) {

            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes"));
        }

    }

    @Test
    public void updateDescriptionOfAptitudeTest(){

        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes");

        assertTrue(aptitudeDao.updateDescriptionOfAptitude(Const.VALID_APTITUDE_ID,Const.VALID_DESCRIPTION));

        try {
            assertFalse(aptitudeDao.updateDescriptionOfAptitude(Const.INVALID_APTITUDE_ID, Const.VALID_DESCRIPTION));
        }catch(Exception e){
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes"));
        }
        try {
            assertFalse(aptitudeDao.updateDescriptionOfAptitude(Const.VALID_APTITUDE_ID, Const.INVALID_DESCRIPTION));
        }catch (Exception e) {
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes"));
        }

    }

    @Test
    public void updateServiceTypeOfAptitudeTest(){

        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes");

        assertTrue(aptitudeDao.updateServiceTypeOfAptitude(Const.VALID_APTITUDE_ID,Const.SERVICETYPE_ID));

        assertTrue(aptitudeDao.updateServiceTypeOfAptitude(Const.VALID_APTITUDE_ID,Const.SERVICETYPE2_ID));

        try {
            assertFalse(aptitudeDao.updateServiceTypeOfAptitude(Const.VALID_APTITUDE_ID, Const.INVALID_SERVICE_TYPE_ID));
        }catch (Exception e) {
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes"));
        }
        try{
            assertFalse(aptitudeDao.updateServiceTypeOfAptitude(Const.INVALID_APTITUDE_ID, Const.SERVICETYPE2_ID));
        }catch (Exception e) {
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes"));
        }
    }

    @Test
    public void removeAptitudeTest(){

        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes");

        assertTrue(aptitudeDao.removeAptitude(Const.VALID_APTITUDE_ID));

        assertFalse(aptitudeDao.removeAptitude(Const.INVALID_APTITUDE_ID));

        em.flush();

        assertEquals(--count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes"));

    }

    @Test
    public void getAptitudeIdTest(){

        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes");

        assertEquals(1,aptitudeDao.getAptitudeId(Const.USER3_ID,Const.SERVICETYPE_ID));

        assertEquals(2,aptitudeDao.getAptitudeId(Const.USER3_ID,Const.SERVICETYPE2_ID));

        assertEquals(-1,aptitudeDao.getAptitudeId(Const.USER3_ID,Const.SERVICETYPE3_ID));

        assertEquals(-1,aptitudeDao.getAptitudeId(Const.USER_ID,Const.SERVICETYPE2_ID));

        assertEquals(-1,aptitudeDao.getAptitudeId(Const.INVALIDAD_USER_ID,Const.SERVICETYPE2_ID));

        assertEquals(-1,aptitudeDao.getAptitudeId(Const.USER3_ID,Const.INVALID_SERVICE_TYPE_ID));

        assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes"));
    }

}
