package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.AptitudeDao;
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
        List<Aptitude> martinAptitudes = aptitudeDao.getAptitudesOfUser(3);
        assertEquals(2,martinAptitudes.size());

        List<Aptitude> carlosAptitudes = aptitudeDao.getAptitudesOfUser(4);
        assertEquals(2,carlosAptitudes.size());

        List<Aptitude> julioAptitudes = aptitudeDao.getAptitudesOfUser(5);
        assertEquals(0,julioAptitudes.size());

        List<Aptitude> florenciaAptitudes = aptitudeDao.getAptitudesOfUser(2);
        assertEquals(0,florenciaAptitudes.size());

    }
    @Test
    public void insertAptitudeTest(){

        assertTrue(aptitudeDao.insertAptitude(3,3,"Tambien soy Obrero"));

        assertFalse(aptitudeDao.insertAptitude(40,3,"Este proveedor no existe"));

        assertFalse(aptitudeDao.insertAptitude(3,40,"esta aptitud no existe"));

        assertFalse(aptitudeDao.insertAptitude(1,3,"Segundo no es proveedor"));


    }

    @Test
    public void updateAptitudeTest(){
        assertTrue( aptitudeDao.updateAptitude(2,"Martin es pintor"));

        assertFalse(aptitudeDao.updateAptitude(402,"Martin es pintor"));
    }
}
