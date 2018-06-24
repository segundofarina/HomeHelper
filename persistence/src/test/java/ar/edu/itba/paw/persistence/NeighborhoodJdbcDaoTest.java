package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.interfaces.daos.NeighborhoodDao;
import ar.edu.itba.paw.model.Neighborhood;
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
@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Rollback
@Transactional

public class NeighborhoodJdbcDaoTest {

    @PersistenceContext
    private EntityManager em;

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
//        for(Neighborhood n : neighborhoodDao.getAllNeighborhoods()){
//            System.out.println(n.getNgId());
//        }

        neighborhoodDao.insertNeighborhood("Almagro");

//        for(Neighborhood n : neighborhoodDao.getAllNeighborhoods()){
//            System.out.println(n.getNgId());
//        }
        //assertEquals("",++count,neighborhoodDao.insertNeighborhood("Almagro"));

        assertEquals(count+1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"neighborhoods"));
    }

    @Test
    public void getAllNeighborhoodsTest(){

        assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate,"neighborhoods"),neighborhoodDao.getAllNeighborhoods().size());
        for(Neighborhood n : neighborhoodDao.getAllNeighborhoods()){
            System.out.println(n.getNgId());
        }
    }
}
