package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Appointment;
import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AppointmentClientListDto {
    private List<AppointmentClientDto> appointments;

    public AppointmentClientListDto() {
    }

    public AppointmentClientListDto(List<Appointment> appointments, Locale locale, MessageSource messageSource) {
        this.appointments = new ArrayList<>();

        for(Appointment appointment: appointments){
            this.appointments.add(new AppointmentClientDto(appointment,locale,messageSource));
        }
    }

    public List<AppointmentClientDto> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentClientDto> appointments) {
        this.appointments = appointments;
    }
}
