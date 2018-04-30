package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
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


import javax.sql.DataSource;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

    @Sql("classpath:schema.sql")
    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration(classes = TestConfig.class)
    public class UserJdbcDaoTest {
        private static final String PASSWORD = "Password";
        private static final String USERNAME = "Username";
        private static final String FIRSTNAME = "Jorge";
        private static final String LASTNAME = "Abayu";
        private static final String EMAIL = "jorgito@yo.com";
        private static final String PHONE = "1123453421";

        @Autowired
        private DataSource ds;

        @Autowired
        private UserDao userDao;

        private JdbcTemplate jdbcTemplate;
        @Before
        public void setUp() {
            jdbcTemplate = new JdbcTemplate(ds);
            JdbcTestUtils.deleteFromTables(jdbcTemplate, "posts","serviceProviders","serviceTypes","users");
        }
        @Test
        public void testCreate() {
            final User user = userDao.create(USERNAME, PASSWORD,FIRSTNAME,LASTNAME,EMAIL,PHONE);
            assertNotNull(user);
            assertEquals(USERNAME, user.getUsername());
            assertEquals(PASSWORD, user.getPassword());
           // assertEquals(0,user.getId());
            assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
        }

        public static User insertDummyUser(UserDao userDao){
            final User user = userDao.create(USERNAME, PASSWORD,FIRSTNAME,LASTNAME,EMAIL,PHONE);
            return user;
        }
    }

