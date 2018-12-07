package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.Status;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {

    List<Appointment> getAppointmentsByProviderId(int providerId);

    List<Appointment> getAppointmentsByUserId(int userId);

    List<Appointment> getAppointmentsByProviderId(int providerId, Status status);

    Appointment getAppointment(int appointmentId);

    Appointment addAppointment(int clientId, int providerId, int serviceTypeId, String date, String address, String jobDescripcion);

    boolean confirmAppointment(int appointmentId);

    boolean completedAppointment(int appointmentId);

    /* Returns pending and confirmed */
    List<Appointment> getPendingAppointmentWithProviderId(int providerId);

    /* Returns pending and confirmed */
    List<Appointment> getPendingAppointmentWithUserId(int userId);

    /* Returns reject and done */
    List<Appointment> getCompleteAppointmentWithProviderId(int providerId);

    /* Returns reject and done */
    List<Appointment> getCompleteAppointmentWithUserId(int userId);

    boolean updateDateOfAppointment(int appointmentId, String date);

    boolean rejectAppointment(int appointmentId);

    List<Appointment> getLatestPendingAppointmentWithProviderId(int providerId);

    Optional<Review> reviewAppointment(int appointmentId, int userId, int quality, int cleanness, int price, int punctuality, int treatment, String comment);

    Appointment getLastAppointment(int userId);
}
