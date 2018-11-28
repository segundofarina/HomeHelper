package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Appointment;

import java.util.Date;

public class AppointmentDto {
    private int appointmentId;
    private ServiceTypeDto serviceTtype;
    private ClientDto client;
    private ProviderDto provider;
    private String address;
    private Date date;
    private String jobDescription;

    public AppointmentDto() {
    }

    public AppointmentDto(Appointment appointment) {
        this.appointmentId = appointment.getAppointmentId();
        this.serviceTtype = new ServiceTypeDto(appointment.getServiceType());
        this.client = new ClientDto(appointment.getClient());
        this.provider = new ProviderDto(appointment.getProvider());
        this.address = appointment.getAddress();
        this.date = appointment.getDate();
        this.jobDescription = appointment.getJobDescripcion();
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public ServiceTypeDto getServiceTtype() {
        return serviceTtype;
    }

    public ClientDto getClient() {
        return client;
    }

    public ProviderDto getProvider() {
        return provider;
    }

    public String getAddress() {
        return address;
    }

    public Date getDate() {
        return date;
    }

    public String getJobDescription() {
        return jobDescription;
    }
}
