package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.daos.AppointmentDao;
import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.AppointmentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static testUtils.AppointmentTestUtils.assetEqualsAppointments;
import static testUtils.AppointmentTestUtils.dummyAppointment;
import static testUtils.AppointmentTestUtils.dummyAppointments;
import static testUtils.SProviderTestUtils.dummySProvider;
import static testUtils.UserTestUtils.dummyUser;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentServiceImplTest {

    @Mock
    private AppointmentDao appointmentDaoMock;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;


    @Test
    public void getAppointmentsByProviderId() {

        SProvider sProvider = dummySProvider();

        List<Appointment> expected = dummyAppointments(10);

        when(appointmentDaoMock.getAppointmentsByProviderId(sProvider.getId())).thenReturn(expected);

        List<Appointment> actual = appointmentService.getAppointmentsByProviderId(sProvider.getId());

        assertTrue(actual.containsAll(expected));

        assertTrue(expected.containsAll(actual));

        verify(appointmentDaoMock, times(1)).getAppointmentsByProviderId(anyInt());
    }

    @Test
    public void getAppointmentsByUserId() {
        User user = dummyUser();

        List<Appointment> expected = dummyAppointments(10);

        when(appointmentDaoMock.getAppointmentsByUserId(user.getId())).thenReturn(expected);

        List<Appointment> actual = appointmentService.getAppointmentsByUserId(user.getId());

        assertTrue(actual.containsAll(expected));

        assertTrue(expected.containsAll(actual));

        verify(appointmentDaoMock, times(1)).getAppointmentsByUserId(anyInt());
    }


    @Test
    public void getAppointment() {
        Appointment expected = dummyAppointment();

        when(appointmentDaoMock.getAppointment(expected.getAppointmentId())).thenReturn(Optional.ofNullable(expected));

        Appointment actual = appointmentService.getAppointment(expected.getAppointmentId());

        assetEqualsAppointments(actual, expected);

        verify(appointmentDaoMock, times(1)).getAppointment(anyInt());

    }

}
