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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @PersistenceContext
    private EntityManager em;

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

        assertEquals(1,appointmentDao.getAppointmentsByProviderId(Const.SERVICETYPE3_ID).size());

        assertEquals(0,appointmentDao.getAppointmentsByProviderId(Const.SPROVIDER_ID).size());

        assertEquals(0,appointmentDao.getAppointmentsByProviderId(Const.INVALID_SERVICE_ID).size());

    }
    @Test
    public void getAppointmentsByUserIdTest(){

        assertEquals(0,appointmentDao.getAppointmentsByUserId(Const.INVALIDAD_USER_ID).size());

        assertEquals(0,appointmentDao.getAppointmentsByUserId(Const.USER2_ID).size());
    }

    @Test
    public void getAppointmentTest(){

        appointmentDao.getAppointment(Const.VALID_APPOINTMENT_ID2);

        appointmentDao.getAppointment(Const.INVALID_APPOINTMENT_ID);
    }
    @Test
    public void addAppointmentTest(){

        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "appointments");

        appointmentDao.addAppointment(Const.USER_ID,Const.SPROVIDER3_ID,Const.SERVICETYPE3_ID,Timestamp.from(Instant.now()),Const.VALID_ADDRESS,Const.VALID_JOBDESCRIPTION);

        assertEquals(++count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "appointments"));
        try {
            appointmentDao.addAppointment(Const.USER_ID, Const.INVALID_SERVICE_PROVIDER_ID, Const.SERVICETYPE3_ID, Timestamp.from(Instant.now()), Const.VALID_ADDRESS, Const.VALID_JOBDESCRIPTION);
        }catch(Exception e){
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "appointments"));
        }
        try{
            appointmentDao.addAppointment(Const.INVALIDAD_USER_ID,Const.SPROVIDER_ID,Const.SERVICETYPE3_ID,Timestamp.from(Instant.now()),Const.VALID_ADDRESS,Const.VALID_JOBDESCRIPTION);
        }catch(Exception e){
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "appointments"));
        }
        try{
            appointmentDao.addAppointment(Const.USER_ID,Const.INVALID_SERVICE_PROVIDER_ID,Const.SERVICETYPE3_ID,Timestamp.from(Instant.now()),Const.VALID_ADDRESS,Const.VALID_JOBDESCRIPTION);
        }catch(Exception e){
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "appointments"));
        }
        try{
            appointmentDao.addAppointment(Const.USER_ID,Const.SPROVIDER_ID,Const.INVALID_SERVICE_TYPE_ID,Timestamp.from(Instant.now()),Const.VALID_ADDRESS,Const.VALID_JOBDESCRIPTION);
        }catch(Exception e){
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "appointments"));
        }
    }

    @Test
    public void updateStatusOfAppointmentTest(){

        boolean ans = appointmentDao.updateStatusOfAppointment(Const.VALID_APPOINTMENT_ID1,Status.Confirmed);

        em.flush();

        assertTrue(ans);

        ans = appointmentDao.updateStatusOfAppointment(Const.VALID_APPOINTMENT_ID1,Status.Done);

        em.flush();

        assertTrue(ans);

        ans = appointmentDao.updateStatusOfAppointment(Const.INVALID_APPOINTMENT_ID,Status.Confirmed);

        em.flush();

        assertFalse(ans);
    }
    @Test
    public void updateDateOfAppointmentTest(){

        Timestamp date = Timestamp.from(Instant.now());

        assertTrue( appointmentDao.updateDateOfAppointment(Const.VALID_APPOINTMENT_ID1, date));

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
