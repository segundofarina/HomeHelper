package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.PostDao;
import ar.edu.itba.paw.interfaces.SProviderDao;
import ar.edu.itba.paw.interfaces.STypeDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.Post;
import ar.edu.itba.paw.model.ServiceType;
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

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class PostJdbcDaoTest {
    private int USER_ID;
    private int SERVICE_TYPE_ID;
    private final static String TITLE = "title";
    private final static String DESCRIPTION = "descrition";

    @Autowired
    private PostDao postDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private STypeDao sTypeDao;
    @Autowired
    private SProviderDao sProviderDao;


    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "posts","serviceProviders","serviceTypes","users");
        /* Add User and Service Type */
        SERVICE_TYPE_ID = STypeJdbcDaoTest.insertDummyServiceType(sTypeDao).getServiceTypeId();
        USER_ID = UserJdbcDaoTest.insertDummyUser(userDao).getId();
        sProviderDao.create(USER_ID);
    }

    @Test
    public void testCreate() {
        final Post post = postDao.create(USER_ID, SERVICE_TYPE_ID, TITLE, DESCRIPTION);
        assertNotNull(post);
        assertEquals(1,post.getIdPost());
        assertEquals(TITLE,post.getTitle());
        assertEquals(DESCRIPTION,post.getDescription());
    }


    @Test
    public void testGetPostWithUserId() {
        postDao.create(USER_ID, SERVICE_TYPE_ID, TITLE, DESCRIPTION);
        final List<Post> list = postDao.getPostWithUserId(USER_ID);

        for(Post p : list){

        }
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

    public static Post createDummyPost(PostDao postDao, User user, ServiceType serviceType){
        return postDao.create(user.getId(), serviceType.getServiceTypeId(), TITLE, DESCRIPTION);
    }
}
