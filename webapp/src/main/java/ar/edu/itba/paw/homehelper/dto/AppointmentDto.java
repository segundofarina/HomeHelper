package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Appointment;

import java.util.Date;

public class AppointmentDto {
    private int id;
    private ServiceTypeDto serviceType;
    private BasicProviderDto provider;
    private String address;
    private String date;
    private String description;
    private boolean hasReview;
    private AppointmentStatusDto status;

    public AppointmentDto() {
    }

    public AppointmentDto(Appointment appointment) {
        this.id = appointment.getAppointmentId();
        this.serviceType = new ServiceTypeDto(appointment.getServiceType());
        this.address = appointment.getAddress();
        this.date = appointment.getDateDMY(); // TODO: Date should be formated according to given locale
        this.description = appointment.getJobDescripcion();
        this.provider = new BasicProviderDto(appointment.getProvider());
        this.hasReview = appointment.hasClientReview();
        this.status = new AppointmentStatusDto(appointment.getStatus());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ServiceTypeDto getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceTypeDto serviceType) {
        this.serviceType = serviceType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BasicProviderDto getProvider() {
        return provider;
    }

    public void setProvider(BasicProviderDto provider) {
        this.provider = provider;
    }

    public boolean isHasReview() {
        return hasReview;
    }

    public void setHasReview(boolean hasReview) {
        this.hasReview = hasReview;
    }

    public AppointmentStatusDto getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatusDto status) {
        this.status = status;
    }
}
