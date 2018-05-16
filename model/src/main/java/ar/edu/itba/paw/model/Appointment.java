package ar.edu.itba.paw.model;

import java.sql.Timestamp;

public class Appointment {
    private int appointmentId;
    private User client;
    private Aptitude aptitude;
    private Timestamp date;
    private String address;
    private Status status;
    private String jobDescripcion;

    public Appointment(int appointmentId, User client, Aptitude aptitude, Timestamp date, String address, Status estatus, String jobDescripcion) {
        this.appointmentId = appointmentId;
        this.client = client;
        this.aptitude = aptitude;
        this.date = date;
        this.address = address;
        this.status = estatus;
        this.jobDescripcion = jobDescripcion;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public User getClient() {
        return client;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public Status getStatus() {
        return status;
    }

    public String getJobDescripcion() {
        return jobDescripcion;
    }

    public void setStatus(Status estatus) {
        this.status = estatus;
    }

    public Aptitude getAptitude() {
        return aptitude;
    }
}
