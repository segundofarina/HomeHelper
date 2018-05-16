package ar.edu.itba.paw.model;

import java.sql.Timestamp;
import java.util.Date;

public class Appointment {
    private int appointmentId;
    private User client;
    private SProvider provider;
    private ServiceType serviceType;
    private Date date;
    private String address;
    private Status status;
    private String jobDescripcion;
    private boolean clientReview;

    public Appointment(int appointmentId, User client, SProvider provider, ServiceType serviceType, Date date, String address, Status estatus, String jobDescripcion,boolean clientReview) {
        this.appointmentId = appointmentId;
        this.client = client;
        this.provider = provider;
        this.serviceType = serviceType;
        this.date = date;
        this.address = address;
        this.status = estatus;
        this.jobDescripcion = jobDescripcion;
        this.clientReview = clientReview;
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

    public Date getDate() {
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

    public boolean isClientReview() {
        return clientReview;
    }
}
