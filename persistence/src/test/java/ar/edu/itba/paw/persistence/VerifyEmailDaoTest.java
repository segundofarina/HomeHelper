package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.daos.VerifyEmailDao;
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

import java.util.Optional;

import static ar.edu.itba.paw.persistence.Const.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback
public class VerifyEmailDaoTest {

    @Autowired
    private DataSource ds;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private VerifyEmailDao verifyEmailDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void insertTest() {
        int count = JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "verifyUsers");
        assertTrue(verifyEmailDao.insert(USER4_ID, "asdasdasdasdasd"));
        em.flush();
        assertEquals(++count, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "verifyUsers"));

        try {
            verifyEmailDao.insert(USER_ID, "Holaohaosdj");
        } catch (Exception e) {

        }
        assertEquals(count, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "verifyUsers"));

    }

    @Test
    public void getKeyTest() {
        Optional<String> keyOp = verifyEmailDao.getKey(USER_ID);
        assertTrue(keyOp.isPresent());
        assertEquals(USER_KEY, keyOp.get());

        keyOp = verifyEmailDao.getKey(USER3_ID);
        assertFalse(keyOp.isPresent());

    }

    @Test
    public void getUserIdTest() {
        Optional<Integer> idOp = verifyEmailDao.getUserId(USER_KEY);
        assertTrue(idOp.isPresent());
        assertEquals(USER_ID, idOp.get().intValue());

        idOp = verifyEmailDao.getUserId("ihdfiuhfdiu98899889");
        assertFalse(idOp.isPresent());
    }

    @Test
    public void deleteEntryTest() {
        int count = JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "verifyUsers");
        assertTrue(verifyEmailDao.deleteEntry(USER_KEY));
        em.flush();
        assertEquals(--count, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "verifyUsers"));

        assertFalse(verifyEmailDao.deleteEntry("782378783278"));
        em.flush();
        assertEquals(count, JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "verifyUsers"));
    }


}
