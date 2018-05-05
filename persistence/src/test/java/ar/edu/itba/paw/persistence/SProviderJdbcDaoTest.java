package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.daos.SProviderDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.*;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class SProviderJdbcDaoTest extends AbstractTransactionalJUnit4SpringContextTests {




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
    }

    @Test
    public void testCreate() {
        int count = JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "serviceProviders");
        final Optional<SProvider> sProvider = sProviderDao.create(Const.USER_ID,"Hola que tal");
        assertNotNull(sProvider);
        assertTrue(sProvider.isPresent());
        assertEquals(Const.USER_ID, sProvider.get().getId());
        assertEquals(count+1,JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "serviceProviders"));
    }

    @Test
    public void testGetServiceProviders() {


        final List<SProvider> sProviders = sProviderDao.getServiceProviders();
        assertNotNull(sProviders);
        assertTrue(containsSpId(sProviders,Const.SPROVIDER_ID) );
        assertTrue(containsSpId(sProviders,Const.SPROVIDER_ID) );
        assertEquals(3,sProviders.size());
        assertFalse(containsSpId(sProviders,Const.USER_ID));
    }

    @Test
    public void testGetServiceProviderWithUserId() {

        final Optional<SProvider> sProvider = sProviderDao.getServiceProviderWithUserId(Const.SPROVIDER_ID);
        assertNotNull(sProvider);
        assertTrue(sProvider.isPresent());
        assertEquals(Const.SPROVIDER_ID, sProvider.get().getId());
    }



    public boolean containsSpId(List<SProvider> list, int id){
        for (SProvider s : list){
            if(s.getId() == id){
                return true;
            }
        }
        return false;
    }





}
