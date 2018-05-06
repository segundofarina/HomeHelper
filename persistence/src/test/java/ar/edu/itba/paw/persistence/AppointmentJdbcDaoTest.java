package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.AppointmentDao;
import ar.edu.itba.paw.model.Status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class AppointmentJdbcDaoTest {

    @Autowired
    AppointmentDao appointmentDao;

    @Before
    public void setUp() {

    }
    @Test
    public void getAppointmentsByProviderIdTest(){

        assertEquals(1,appointmentDao.getAppointmentsByProviderId(3).size());

        assertEquals(0,appointmentDao.getAppointmentsByProviderId(4).size());

    }
    @Test
    public void getAppointmentsByUserIdTest(){

        assertEquals(0,appointmentDao.getAppointmentsByUserId(2).size());

        assertEquals(0,appointmentDao.getAppointmentsByUserId(3).size());
    }

    @Test
    public void getAppointmentTest(){

        appointmentDao.getAppointment(2);

        appointmentDao.getAppointment(40);
    }
    @Test
    public void getAppointmentIdTest(){
        Optional<Integer> id = appointmentDao.getAppointmentId(1,3,"10-05-2018" ,"cuba 2546 6p");

        assertTrue(id.isPresent());

        assertEquals(1,id.get().intValue());

        assertEquals(Optional.empty(),appointmentDao.getAppointmentId(2,3,"10-05-2018","cuba 2546 6p"));

        assertEquals(Optional.empty(),appointmentDao.getAppointmentId(1,30,"10-05-2018","cuba 2546 6p"));

        assertEquals(Optional.empty(),appointmentDao.getAppointmentId(1,3,"05-05-2018","cuba 2546 6p"));

        assertEquals(Optional.empty(),appointmentDao.getAppointmentId(1,3,"10-05-2018","cuba 2546 12p"));
    }
    @Test
    public void addAppointmentTest(){

        assertTrue(appointmentDao.addAppointment(1,4,3,"05-05-2018","aguilar 2547 12A","Carlos soy segundo no me rehaces para pared??"));

        assertFalse(appointmentDao.addAppointment(1,4,40,"05-05-2018","aguilar 2547 12A","Carlos soy segundo no me rehaces para pared??"));

        assertFalse(appointmentDao.addAppointment(1,1,3,"05-05-2018","aguilar 2547 12A","Segundo, soy segundo no me rehaces para pared??"));

        assertTrue(appointmentDao.addAppointment(1,4,1,"05-05-2018","aguilar 2547 12A","Carlos soy segundo no me pintas para pared??"));

        assertFalse(appointmentDao.addAppointment(100,4,3,"05-05-2018","aguilar 2547 12A","Carlos soy segundo no me rehaces para pared??"));

        assertFalse(appointmentDao.addAppointment(1,100,3,"05-05-2018","aguilar 2547 12A","Carlos soy segundo no me rehaces para pared??"));

        assertFalse(appointmentDao.addAppointment(1,4,100,"05-05-2018","aguilar 2547 12A","Carlos soy segundo no me rehaces para pared??"));
    }

    @Test
    public void updateStatusOfAppointmentTest(){

        assertTrue(appointmentDao.updateStatusOfAppointment(1,Status.Confirmed));

        assertFalse(appointmentDao.updateStatusOfAppointment(100,Status.Confirmed));
    }
    @Test
    public void completedAppointmentTest(){

        assertTrue(appointmentDao.updateStatusOfAppointment(1,Status.Done));

        assertFalse(appointmentDao.updateStatusOfAppointment(100,Status.Done));

    }

}
