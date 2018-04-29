package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.SProviderDao;
import ar.edu.itba.paw.model.SProvider;
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

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class SProviderJdbcDaoTest {
    private final static int USER_ID = 1;
    private final static int POST_ID = 1;


    @Autowired
    private DataSource ds;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SProviderDao sProviderDao;


    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        //JdbcTestUtils.deleteFromTables(jdbcTemplate, "serviceProviders");
    }

    @Test
    public void testCreate() {
/*        final SProvider sProvider = sProviderDao.create(USER_ID, POST_ID);
        assertNotNull(sProvider);
        assertTrue(sProvider.getUserId() == USER_ID);
        assertTrue(sProvider.getPosts().contains(POST_ID));
  */  }
/*
    @Test
    public void testGetServiceProviders() {
        final List<SProvider> sProviders = sProviderDao.getServiceProviders();
        assertNotNull(sProviders);
        //assertTrue(sProviders.contains() == USER_ID);
        //assertTrue(sProvider.getPosts().contains(POST_ID));
    }

    @Test
    public void testGetServiceProviderWithUserId() {
        final SProvider sProvider = sProviderDao.getServiceProviderWithUserId(USER_ID);
        assertNotNull(sProvider);
        assertEquals(USER_ID, sProvider.getUserId());
        assertTrue(sProvider.getPosts().contains(POST_ID));
    }
*/
}
