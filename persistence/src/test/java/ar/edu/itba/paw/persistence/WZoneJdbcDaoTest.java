package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.daos.WZoneDao;
import ar.edu.itba.paw.model.Neighborhood;
import ar.edu.itba.paw.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;


import javax.sql.DataSource;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static junit.framework.Assert.*;
import static org.junit.Assert.assertEquals;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Rollback
public class WZoneJdbcDaoTest{

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

       wZoneDao.insertWorkingZoneOfProvider(Const.USER4_ID,Const.VALID_NG);

        assertEquals(++count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "workingzones"));

        try {
            wZoneDao.insertWorkingZoneOfProvider(Const.INVALIDAD_USER_ID, Const.INVALID_NG);
        }catch (Exception e){
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "workingzones"));
        }
        try {
            wZoneDao.insertWorkingZoneOfProvider(Const.USER_ID, Const.INVALID_NG);
        }catch (Exception e){
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "workingzones"));
        }
    }
    @Test
    public void getWorkingZonesOfProvider() {

        assertEquals(0,wZoneDao.getWorkingZonesOfProvider(Const.INVALID_SERVICE_PROVIDER_ID).size());

        assertEquals(3, wZoneDao.getWorkingZonesOfProvider(Const.SPROVIDER3_ID).size());

        assertEquals(0, wZoneDao.getWorkingZonesOfProvider(Const.INVALID_SERVICE_PROVIDER_ID).size());

    }
}
