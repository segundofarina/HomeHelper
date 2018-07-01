package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.daos.TemporaryImagesDao;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback
public class TemporaryImageHibernateTest {

    @Autowired
    private DataSource ds;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TemporaryImagesDao temporaryImagesDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void insertImageTest() {

        int count = JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "temporaryImages");
        temporaryImagesDao.insertImage("Any String you want".getBytes());
        em.flush();
        assertEquals(++count, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "temporaryImages"));
    }

    @Test
    public void deleteImage() {
        int count = JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "temporaryImages");
        assertTrue(temporaryImagesDao.deleteImage(1));
        em.flush();
        assertEquals(--count, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "temporaryImages"));
    }


    @Test
    public void getImage() {

    }

}
