package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.PostDao;
import ar.edu.itba.paw.interfaces.STypeDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.Post;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

import static junit.framework.TestCase.assertNotNull;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class PostJdbcDaoTest {
    private int USER_ID;
    private int SERVICE_TYPE_ID;
    private final static String SERVICE_TYPE_NAME = "STN";
    private final static String TITLE = "title";
    private final static String DESCRIPTION = "descrition";

    @Autowired
    private PostDao postDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private STypeDao sTypeDao;


    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);

        /* Add User and Service Type */
        //SERVICE_TYPE_ID = sTypeDao.create(SERVICE_TYPE_NAME).getServiceTypeId();
        //USER_ID = userDao.create("username", "password", "firstname", "lastname", "email", "phone").getId();
    }

    @Test
    public void testCreate() {
        //final Post post = postDao.create(USER_ID, SERVICE_TYPE_ID, TITLE, DESCRIPTION);
        //assertNotNull(post);
    }

    @Test
    public void testGetPostWithUserId() {

    }

    @Test
    public void testGetPostWithId() {

    }

    @Test
    public void testGetPosts() {

    }

    @Test
    public void testUpdatePostWithId() {

    }

    @Test
    public void testDeletePostWithId() {

    }

}
