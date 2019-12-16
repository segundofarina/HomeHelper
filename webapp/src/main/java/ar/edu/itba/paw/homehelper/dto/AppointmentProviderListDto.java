package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Appointment;
import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AppointmentProviderListDto {
    private List<AppointmentProviderDto> appointments;

    public AppointmentProviderListDto() {
    }

    public AppointmentProviderListDto(List<Appointment> appointments, Locale locale, MessageSource messageSource,String baseUri) {
        this.appointments = new ArrayList<>();

        for(Appointment appointment: appointments){
            this.appointments.add(new AppointmentProviderDto(appointment,locale,messageSource));
        }
    }

    public List<AppointmentProviderDto> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentProviderDto> appointments) {
        this.appointments = appointments;
    }
}
