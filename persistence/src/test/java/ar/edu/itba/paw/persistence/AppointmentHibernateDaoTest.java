package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.AppointmentDao;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.Instant;

import static ar.edu.itba.paw.persistence.Const.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class AppointmentHibernateDaoTest {

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
    public void getAppointmentsByProviderIdTest() {

        assertEquals(2, appointmentDao.getAppointmentsByProviderId(SERVICETYPE3_ID).size());

        assertEquals(2, appointmentDao.getAppointmentsByProviderId(SPROVIDER_ID).size());

        assertEquals(0, appointmentDao.getAppointmentsByProviderId(INVALID_SERVICE_ID).size());

    }

    @Test
    public void getAppointmentsByUserIdTest() {

        assertEquals(0, appointmentDao.getAppointmentsByUserId(INVALIDAD_USER_ID).size());

        assertEquals(2, appointmentDao.getAppointmentsByUserId(USER2_ID).size());
    }

    @Test
    public void getAppointmentTest() {

        appointmentDao.getAppointment(VALID_APPOINTMENT_ID2);

        appointmentDao.getAppointment(INVALID_APPOINTMENT_ID);
    }

    @Test
    public void addAppointmentTest() {

        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "appointments");

        appointmentDao.addAppointment(USER_ID, SPROVIDER3_ID, SERVICETYPE3_ID, Timestamp.from(Instant.now()), VALID_ADDRESS, VALID_JOBDESCRIPTION);
        em.flush();
        assertEquals(++count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "appointments"));
        try {
            appointmentDao.addAppointment(USER_ID, INVALID_SERVICE_PROVIDER_ID, SERVICETYPE3_ID, Timestamp.from(Instant.now()), VALID_ADDRESS, VALID_JOBDESCRIPTION);
        } catch (Exception e) {
            em.flush();
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "appointments"));
        }
        try {
            appointmentDao.addAppointment(INVALIDAD_USER_ID, SPROVIDER_ID, SERVICETYPE3_ID, Timestamp.from(Instant.now()), VALID_ADDRESS, VALID_JOBDESCRIPTION);
        } catch (Exception e) {
            em.flush();
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "appointments"));
        }
        try {
            appointmentDao.addAppointment(USER_ID, INVALID_SERVICE_PROVIDER_ID, SERVICETYPE3_ID, Timestamp.from(Instant.now()), VALID_ADDRESS, VALID_JOBDESCRIPTION);
        } catch (Exception e) {
            em.flush();
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "appointments"));
        }
        try {
            appointmentDao.addAppointment(USER_ID, SPROVIDER_ID, INVALID_SERVICE_TYPE_ID, Timestamp.from(Instant.now()), VALID_ADDRESS, VALID_JOBDESCRIPTION);
        } catch (Exception e) {
            em.flush();
            assertEquals(count, JdbcTestUtils.countRowsInTable(jdbcTemplate, "appointments"));
        }
    }

    @Test
    public void updateStatusOfAppointmentTest() {

        boolean ans = appointmentDao.updateStatusOfAppointment(VALID_APPOINTMENT_ID1, Status.Confirmed);

        em.flush();

        assertTrue(ans);

        ans = appointmentDao.updateStatusOfAppointment(VALID_APPOINTMENT_ID1, Status.Done);

        em.flush();

        assertTrue(ans);

        ans = appointmentDao.updateStatusOfAppointment(INVALID_APPOINTMENT_ID, Status.Confirmed);

        em.flush();

        assertFalse(ans);
    }

    @Test
    public void updateDateOfAppointmentTest() {

        Timestamp date = Timestamp.from(Instant.now());

        assertTrue(appointmentDao.updateDateOfAppointment(VALID_APPOINTMENT_ID1, date));

        assertFalse(appointmentDao.updateDateOfAppointment(INVALID_APPOINTMENT_ID, date));
    }


    @Test
    public void rewviewAppointmentTest(){
        appointmentDao.reviewAppointment(VALID_APPOINTMENT_ID1,USER2_ID,4,4,4,4,4,"Muy bueno");
    }
}
