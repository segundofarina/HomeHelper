package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.model.User;
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

import java.sql.SQLException;
import java.util.Optional;

import static junit.framework.Assert.*;

@Sql("classpath:schema.sql")
    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration(classes = TestConfig.class)
@Transactional
    public class UserJdbcDaoTest {
        private static final String PASSWORD = "Password";
        private static final String USERNAME = "Username";
        private static final String FIRSTNAME = "Jorge";
        private static final String LASTNAME = "Gomez";
        private static final String EMAIL = "jorgito@yo.com";
        private static final String PHONE = "1123453421";
        private static final byte[] IMAGE = new byte[]{1, 0, 1, 0};
        private static final String ADDRESS = "cuba 2546";

        @Autowired
        private DataSource ds;

    @PersistenceContext
    private EntityManager em;

        @Autowired
        private UserDao userDao;

        private JdbcTemplate jdbcTemplate;
        @Before
        public void setUp() {
            jdbcTemplate = new JdbcTemplate(ds);
            //JdbcTestUtils.deleteFromTables(jdbcTemplate, "messages","posts","serviceProviders","serviceTypes","users");
        }
        @Test
        public void testCreate() throws SQLException {
            int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "users");
            final User user = userDao.create(USERNAME, PASSWORD,FIRSTNAME,LASTNAME,EMAIL,PHONE,ADDRESS,IMAGE);
            em.flush();
            assertNotNull(user);
            assertEquals(USERNAME, user.getUsername());
            assertEquals(PASSWORD, user.getPassword());
           // assertEquals(0,user.getId());
            assertEquals(count+1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
        }
        @Test
        public void testFindById(){

            Optional<User> response = userDao.findById(Const.USER_ID);
            assertTrue(response.isPresent());
            assertEquals(Const.USER_USERNAME,response.get().getUsername());
            assertEquals(Const.USER_PASSWORD,response.get().getPassword());
            assertEquals(Const.USER_FIRSTNAME,response.get().getFirstname());
            assertEquals(Const.USER_LASTNAME,response.get().getLastname());
            assertEquals(Const.USER_EMAIL,response.get().getEmail());
            assertEquals(Const.USER_PHONE,response.get().getPhone());

            response = userDao.findById(115);
            assertFalse(response.isPresent());
        }


        public static User insertDummyUser(UserDao userDao) throws SQLException {
            final User user = userDao.create(USERNAME, PASSWORD,FIRSTNAME,LASTNAME,EMAIL,PHONE,ADDRESS,IMAGE);
            return user;
        }

        @Test
        public void testGetProfileImage(){

        }
}

