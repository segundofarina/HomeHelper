package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.CoordenatesDao;
import ar.edu.itba.paw.model.CoordenatesPoint;
import javafx.collections.transformation.SortedList;
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
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static junit.framework.TestCase.assertEquals;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class CoordenatesHibenateDaoTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    CoordenatesDao coordenatesDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }


    @Test
    public void insertCoordenatesOfProviderTest(){
        List<CoordenatesPoint> coordenatesSet = new ArrayList<>();
        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "coordenates");

        coordenatesSet.add(new CoordenatesPoint(Const.SPROVIDER_ID,1,4,8));
        coordenatesSet.add(new CoordenatesPoint(Const.SPROVIDER_ID,2,5,9));
        coordenatesSet.add(new CoordenatesPoint(Const.SPROVIDER_ID,3,6,10));
        coordenatesSet.add(new CoordenatesPoint(Const.SPROVIDER_ID,4,7,11));
        coordenatesSet.add(new CoordenatesPoint(Const.SPROVIDER_ID,5,8,12));



        coordenatesDao.insertCoordenatesOfProvider(Const.SPROVIDER_ID,coordenatesSet);
        em.flush();
        assertEquals(count + 2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "coordenates"));
    }


}
