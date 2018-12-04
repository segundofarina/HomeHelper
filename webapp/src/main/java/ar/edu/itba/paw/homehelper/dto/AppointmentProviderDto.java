package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Appointment;

public class AppointmentProviderDto {

    private int id;
    private ServiceTypeDto serviceType;
    private BasicUserDto client;
    private String address;
    private String date;
    private String description;
    private boolean hasReview;
    private AppointmentStatusDto status;

    public AppointmentProviderDto() {
    }

    public AppointmentProviderDto(Appointment appointment) {
        this.id = appointment.getAppointmentId();
        this.serviceType = new ServiceTypeDto(appointment.getServiceType());
        this.address = appointment.getAddress();
        this.date = appointment.getDateDMY(); // TODO: Date should be formated according to given locale
        this.description = appointment.getJobDescripcion();
        this.client = new BasicUserDto(appointment.getClient());
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

    public BasicUserDto getClient() {
        return client;
    }

    public void setClient(BasicUserDto client) {
        this.client = client;
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
