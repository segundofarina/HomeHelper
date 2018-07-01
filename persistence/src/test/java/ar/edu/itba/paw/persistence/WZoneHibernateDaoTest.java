package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.WZoneDao;
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

import static ar.edu.itba.paw.persistence.Const.*;
import static org.junit.Assert.assertEquals;


@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class WZoneHibernateDaoTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private DataSource ds;

    @Autowired
    WZoneDao wZoneDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void insertWorkingZoneOfProvider() {

        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "workingzones");

        em.flush();

        wZoneDao.insertWorkingZoneOfProvider(USER4_ID, VALID_NG);

        em.flush();

        assertEquals(++count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "workingzones"));

        try {
            wZoneDao.insertWorkingZoneOfProvider(INVALIDAD_USER_ID, INVALID_NG);
        } catch (Exception e) {
            em.flush();
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "workingzones"));
        }
        try {
            em.flush();
            wZoneDao.insertWorkingZoneOfProvider(USER_ID, INVALID_NG);
        } catch (Exception e) {
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "workingzones"));
        }
    }

    @Test
    public void getWorkingZonesOfProvider() {

        assertEquals(0, wZoneDao.getWorkingZonesOfProvider(INVALID_SERVICE_PROVIDER_ID).size());

        assertEquals(3, wZoneDao.getWorkingZonesOfProvider(SPROVIDER3_ID).size());

        assertEquals(0, wZoneDao.getWorkingZonesOfProvider(INVALID_SERVICE_PROVIDER_ID).size());

    }
}
