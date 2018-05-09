package ar.edu.itba.paw.model;

import java.sql.Timestamp;

public class Appointment {
    private int appointmentId;
    private User client;
    private SProvider provider;
    private ServiceType serviceType;
    private Timestamp date;
    private String address;
    private Status status;
    private String jobDescripcion;

    public Appointment(int appointmentId, User client, SProvider provider, ServiceType serviceType, Timestamp date, String address, Status estatus, String jobDescripcion) {
        this.appointmentId = appointmentId;
        this.client = client;
        this.provider = provider;
        this.serviceType = serviceType;
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

    public SProvider getProvider() {
        return provider;
    }

    public ServiceType getServiceType() {
        return serviceType;
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
}
