package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.AptitudeDao;
import ar.edu.itba.paw.model.Aptitude;
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

import java.util.Set;

import static ar.edu.itba.paw.persistence.Const.*;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
    public void getAptitudesOfUserTest() {

        Set<Aptitude> aptitudes = aptitudeDao.getAptitudesOfUser(USER3_ID);

        assertEquals(2, aptitudes.size());

        assertTrue(aptitudes.contains(new Aptitude(VALID_APTITUDE_ID)));

        assertFalse(aptitudes.contains(new Aptitude(INVALID_APTITUDE_ID)));

        assertEquals(0, aptitudeDao.getAptitudesOfUser(INVALIDAD_USER_ID).size());

    }

    @Test
    public void insertAptitudeTest() {

        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes");

        aptitudeDao.insertAptitude(SPROVIDER_ID, SERVICETYPE3_ID, VALID_DESCRIPTION);
        em.flush();
        assertEquals(++count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes"));

        try {
            aptitudeDao.insertAptitude(INVALID_SERVICE_PROVIDER_ID, SERVICETYPE3_ID, VALID_DESCRIPTION);

        } catch (Exception e) {
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes"));
        }

        try {
            aptitudeDao.insertAptitude(SPROVIDER_ID, INVALID_SERVICE_TYPE_ID, VALID_DESCRIPTION);

        } catch (Exception e) {
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes"));
        }

        try {
            aptitudeDao.insertAptitude(SPROVIDER_ID, SERVICETYPE2_ID, INVALID_DESCRIPTION);

        } catch (Exception e) {

            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes"));
        }

    }

    @Test
    public void updateDescriptionOfAptitudeTest() {

        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes");

        assertTrue(aptitudeDao.updateDescriptionOfAptitude(VALID_APTITUDE_ID, VALID_DESCRIPTION));

        try {
            assertFalse(aptitudeDao.updateDescriptionOfAptitude(INVALID_APTITUDE_ID, VALID_DESCRIPTION));
        } catch (Exception e) {
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes"));
        }
        try {
            assertFalse(aptitudeDao.updateDescriptionOfAptitude(VALID_APTITUDE_ID, INVALID_DESCRIPTION));
        } catch (Exception e) {
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes"));
        }

    }

    @Test
    public void updateServiceTypeOfAptitudeTest() {

        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes");

        assertTrue(aptitudeDao.updateServiceTypeOfAptitude(VALID_APTITUDE_ID, SERVICETYPE_ID));

        assertTrue(aptitudeDao.updateServiceTypeOfAptitude(VALID_APTITUDE_ID, SERVICETYPE2_ID));

        try {
            assertFalse(aptitudeDao.updateServiceTypeOfAptitude(VALID_APTITUDE_ID, INVALID_SERVICE_TYPE_ID));
        } catch (Exception e) {
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes"));
        }
        try {
            assertFalse(aptitudeDao.updateServiceTypeOfAptitude(INVALID_APTITUDE_ID, SERVICETYPE2_ID));
        } catch (Exception e) {
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes"));
        }
    }

    @Test
    public void removeAptitudeTest() {

        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes");

        assertTrue(aptitudeDao.removeAptitude(VALID_APTITUDE_ID));

        assertFalse(aptitudeDao.removeAptitude(INVALID_APTITUDE_ID));

        em.flush();

        //assertEquals(--count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes"));

    }

    @Test
    public void getAptitudeIdTest() {

        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes");

        assertEquals(1, aptitudeDao.getAptitudeId(USER3_ID, SERVICETYPE_ID));

        assertEquals(2, aptitudeDao.getAptitudeId(USER3_ID, SERVICETYPE2_ID));

        assertEquals(-1, aptitudeDao.getAptitudeId(USER3_ID, SERVICETYPE3_ID));

        assertEquals(-1, aptitudeDao.getAptitudeId(USER_ID, SERVICETYPE2_ID));

        assertEquals(-1, aptitudeDao.getAptitudeId(INVALIDAD_USER_ID, SERVICETYPE2_ID));

        assertEquals(-1, aptitudeDao.getAptitudeId(USER3_ID, INVALID_SERVICE_TYPE_ID));

        assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "aptitudes"));
    }

}
