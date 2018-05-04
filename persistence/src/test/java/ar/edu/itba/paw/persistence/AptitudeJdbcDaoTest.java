package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AptitudeDao;
import ar.edu.itba.paw.model.Aptitude;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)

public class AptitudeJdbcDaoTest {

    @Autowired
    AptitudeDao aptitudeDao;

    @Before
    public void setUp() {

    }

    @Test
    public void getByIdTest(){
        List<Aptitude> martinAptitudes = aptitudeDao.getById(3);
        assertEquals(2,martinAptitudes.size());

        List<Aptitude> carlosAptitudes = aptitudeDao.getById(4);
        assertEquals(2,carlosAptitudes.size());

        List<Aptitude> julioAptitudes = aptitudeDao.getById(5);
        assertEquals(0,julioAptitudes.size());

        List<Aptitude> florenciaAptitudes = aptitudeDao.getById(2);
        assertEquals(0,florenciaAptitudes.size());
    }
    @Test
    public void insertAptitudeTest(){
        boolean ans = aptitudeDao.insertAptitude(4,2,"HOLA Soy Segundo el pintor");
        assertTrue(ans);

        ans = aptitudeDao.insertAptitude(1,2,"HOLA Soy Segundo el pintor");
        assertFalse(ans);

    }
}
