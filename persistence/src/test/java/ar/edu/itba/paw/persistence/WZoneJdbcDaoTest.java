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

        assertTrue(wZoneDao.insertWorkingZoneOfProvider(4,1));

        assertFalse(wZoneDao.insertWorkingZoneOfProvider(100,1));

        assertFalse(wZoneDao.insertWorkingZoneOfProvider(1,100));
    }
    @Test
    public void getWorkingZonesOfProvider() {

        List<Neighborhood> ans;

        ans = wZoneDao.getWorkingZonesOfProvider(4);

        assertEquals(null,ans);

        ans = wZoneDao.getWorkingZonesOfProvider(3);

        assertEquals(3,ans.size());

        ans = wZoneDao.getWorkingZonesOfProvider(100);

        assertEquals(null,ans);
    }
}
