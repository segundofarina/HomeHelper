package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.interfaces.daos.NeighborhoodDao;
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

import javax.sql.DataSource;
import static junit.framework.TestCase.assertEquals;
@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Rollback
@Transactional
@Sql("classpath:schema.sql")

public class NeighborhoodJdbcDaoTest {

    @Autowired
    NeighborhoodDao neighborhoodDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void insertNeighborhoodTest(){

        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "neighborhoods");

        assertEquals(++count,neighborhoodDao.insertNeighborhood("Almagro"));

        assertEquals(count,JdbcTestUtils.countRowsInTable(jdbcTemplate,"neighborhoods"));
    }

    @Test
    public void getAllNeighborhoodsTest(){

        assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate,"neighborhoods"),neighborhoodDao.getAllNeighborhoods().size());
    }
}
