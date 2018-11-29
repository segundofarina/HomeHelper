package testUtils;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class AppointmentTestUtils {

    public AppointmentTestUtils() {

    }

    public static Appointment dummyAppointment() {
        Random r = new Random();
        return new Appointment(UserTestUtils.dummyUser(), SProviderTestUtils.dummySProvider(), ServiceTypeTestUtils.dummyServiceType(), new Date(), r.toString(), Status.Pending, r.toString(), false);
    }

    public static List<Appointment> dummyAppointments(int size) {
        List<Appointment> appointmentList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            appointmentList.add(dummyAppointment());
        }
        return appointmentList;
    }

    public static void assetEqualsAppointments(Appointment expected, Appointment actual) {
        assertEquals(expected, actual);
        assertEquals(expected.getAppointmentId(), actual.getAppointmentId());
        assertEquals(expected.hasClientReview(), actual.hasClientReview());
        UserTestUtils.assertEqualsUsers(expected.getClient(), actual.getClient());
        SProviderTestUtils.assertEqualsSProviders(expected.getProvider(), actual.getProvider());
        ServiceTypeTestUtils.assertEqualsServiceTypes(expected.getServiceType(), actual.getServiceType());
    }
}
