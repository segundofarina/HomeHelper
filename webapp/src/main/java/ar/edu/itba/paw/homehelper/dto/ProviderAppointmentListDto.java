package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Appointment;
import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProviderAppointmentListDto {
    private List<ProviderAppointmentDto> appointments;

    public ProviderAppointmentListDto() {
    }

    public ProviderAppointmentListDto(List<Appointment> appointments, Locale locale, MessageSource messageSource) {
        this.appointments = new ArrayList<>();

        for(Appointment appointment: appointments){
            this.appointments.add(new ProviderAppointmentDto(appointment,locale,messageSource));
        }
    }

    public List<ProviderAppointmentDto> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<ProviderAppointmentDto> appointments) {
        this.appointments = appointments;
    }
}
