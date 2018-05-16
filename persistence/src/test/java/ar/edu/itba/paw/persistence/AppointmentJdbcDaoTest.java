package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.AppointmentDao;
import ar.edu.itba.paw.interfaces.daos.AptitudeDao;
import ar.edu.itba.paw.model.Status;
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
import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class AppointmentJdbcDaoTest {

    @Autowired
    AppointmentDao appointmentDao;

    @Autowired
    private DataSource ds;


    private JdbcTemplate jdbcTemplate;
    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }
    @Test
    public void getAppointmentsByProviderIdTest(){

        assertEquals(5,appointmentDao.getAppointmentsByProviderId(Const.SPROVIDER3_ID).size());

        assertEquals(0,appointmentDao.getAppointmentsByProviderId(Const.SPROVIDER_ID).size());

        assertEquals(0,appointmentDao.getAppointmentsByProviderId(Const.INVALID_SERVICE_PROVIDER_ID));

    }
    @Test
    public void getAppointmentsByUserIdTest(){

        assertEquals(0,appointmentDao.getAppointmentsByUserId(Const.INVALIDAD_USER_ID).size());

        assertEquals(1,appointmentDao.getAppointmentsByUserId(Const.USER2_ID).size());
    }

    @Test
    public void getAppointmentTest(){

        appointmentDao.getAppointment(Const.VALID_APPOINTMENT_ID2);

        appointmentDao.getAppointment(Const.INVALID_APPOINTMENT_ID);
    }
    @Test
    public void addAppointmentTest(){

//        assertEquals(null,appointmentDao.addAppointment(Const.USER_ID,Const.APTITUDE,Timestamp.from(Instant.now()),Const.VALID_ADDRESS,Const.VALID_JOBDESCRIPTION));
//
//        assertEquals(null,appointmentDao.addAppointment(Const.INVALIDAD_USER_ID,Const.SPROVIDER_ID,Const.SERVICETYPE3_ID,Timestamp.from(Instant.now()),Const.VALID_ADDRESS,Const.VALID_JOBDESCRIPTION));
//
//        assertEquals(null,appointmentDao.addAppointment(Const.USER_ID,Const.INVALID_SERVICE_PROVIDER_ID,Const.SERVICETYPE3_ID,Timestamp.from(Instant.now()),Const.VALID_ADDRESS,Const.VALID_JOBDESCRIPTION));
//
//        assertEquals(null,appointmentDao.addAppointment(Const.USER_ID,Const.SPROVIDER_ID,Const.INVALID_SERVICE_TYPE_ID,Timestamp.from(Instant.now()),Const.VALID_ADDRESS,Const.VALID_JOBDESCRIPTION));
//
//        assertEquals(null,appointmentDao.addAppointment(Const.USER_ID,Const.INVALID_SERVICE_PROVIDER_ID,Const.SERVICETYPE3_ID,Timestamp.from(Instant.now()),Const.INVALID_ADDRESS,Const.VALID_JOBDESCRIPTION));
//
//        assertEquals(null,appointmentDao.addAppointment(Const.USER_ID,Const.INVALID_SERVICE_PROVIDER_ID,Const.SERVICETYPE3_ID,Timestamp.from(Instant.now()),Const.VALID_ADDRESS,Const.INVALID_JOBDESCRIPTION));

    }

    @Test
    public void updateStatusOfAppointmentTest(){

        assertTrue(appointmentDao.updateStatusOfAppointment(Const.VALID_APPOINTMENT_ID1,Status.Confirmed));

        assertTrue(appointmentDao.updateStatusOfAppointment(Const.VALID_APPOINTMENT_ID1,Status.Done));

        assertFalse(appointmentDao.updateStatusOfAppointment(Const.INVALID_APPOINTMENT_ID,Status.Confirmed));
    }
    @Test
    public void updateDateOfAppointmentTest(){

        Timestamp date = Timestamp.from(Instant.now());

        assertTrue(appointmentDao.updateDateOfAppointment(Const.VALID_APPOINTMENT_ID1, date));

        assertFalse(appointmentDao.updateDateOfAppointment(Const.INVALID_APPOINTMENT_ID,date));
    }

    @Test
    public void removeAppointmentTest(){

        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "appointments");

        assertTrue(appointmentDao.removeAppointment(Const.VALID_APPOINTMENT_ID1));

        assertFalse(appointmentDao.removeAppointment(Const.INVALID_APPOINTMENT_ID));

        assertEquals(--count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "appointments"));

    }
}
