package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Appointment;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class ProviderAppointmentDto {
    private int id;
    private ServiceTypeDto serviceType;
    private BasicUserDto user;
    private String address;
    private String date;
    private String description;
    private boolean hasReview;
    private AppointmentStatusDto status;

    public ProviderAppointmentDto() {
    }

    public ProviderAppointmentDto(Appointment appointment, Locale locale, MessageSource messageSource) {
        this.id = appointment.getAppointmentId();
        this.serviceType = new ServiceTypeDto(appointment.getServiceType(),locale,messageSource);
        this.address = appointment.getAddress();
        this.date = appointment.getDateDMY(locale); 
        this.description = appointment.getJobDescripcion();
        this.user = new BasicUserDto(appointment.getClient());
        this.hasReview = appointment.hasClientReview();
        this.status = new AppointmentStatusDto(appointment.getStatus(),locale,messageSource);
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

    public BasicUserDto getUser() {
        return user;
    }

    public void setUser(BasicUserDto user) {
        this.user = user;
    }
}
