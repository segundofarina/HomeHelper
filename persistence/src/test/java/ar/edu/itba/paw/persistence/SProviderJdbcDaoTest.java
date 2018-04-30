package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.SProviderDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.User;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SProviderJdbcDaoTest {




    @Autowired
    private DataSource ds;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SProviderDao sProviderDao;

    @Autowired
    private UserDao userDao;


    User dUser;

    private int USER_ID;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "posts","serviceProviders","serviceTypes","users");
        dUser = UserJdbcDaoTest.insertDummyUser(userDao);
        USER_ID = dUser.getId();
    }

    @Test
    public void testCreate() {
       final SProvider sProvider = sProviderDao.create(USER_ID);
        assertNotNull(sProvider);
        assertTrue(sProvider.getUserId() == USER_ID);
    }

    @Test
    public void testGetServiceProviders() {

        sProviderDao.create(USER_ID);

        final List<SProvider> sProviders = sProviderDao.getServiceProviders();
        assertNotNull(sProviders);
        assertTrue(containsSpId(sProviders,USER_ID) );
    }

    @Test
    public void testGetServiceProviderWithUserId() {
        sProviderDao.create(USER_ID);

        final SProvider sProvider = sProviderDao.getServiceProviderWithUserId(USER_ID);
        assertNotNull(sProvider);
        assertEquals(USER_ID, sProvider.getUserId());
    }



    public boolean containsSpId(List<SProvider> list, int id){
        for (SProvider s : list){
            if(s.getUserId() == id){
                return true;
            }
        }
        return false;
    }

    public SProvider getSpId(List<SProvider> list, int id){
        for (SProvider s : list){
            if(s.getUserId() == id){
                return s;
            }
        }
        return null;
    }

    public boolean containsSpPost(Set<Integer> list, int id){
        for (Integer post : list){
            if(post == id){
                return true;
            }
        }
        return false;
    }



}
