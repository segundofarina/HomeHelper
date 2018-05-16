package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.Status;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface AppointmentDao {

    List<Appointment> getAppointmentsByProviderId(int providerId);

    List<Appointment> getAppointmentsByUserId(int userId);

    Optional<Appointment> getAppointment(int appointmentId);

    Appointment addAppointment(int clientId, int providerId, int serviceTypeId, Timestamp date, String address, String jobDescripcion);

    boolean updateStatusOfAppointment(int appointmentId, Status status);

    boolean updateDateOfAppointment(int appointmentId, Timestamp date);

    boolean removeAppointment(int appointmentId);

}

