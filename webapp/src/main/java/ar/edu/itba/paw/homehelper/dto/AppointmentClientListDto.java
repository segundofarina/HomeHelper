package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentClientListDto {
    private List<AppointmentClientDto> appointments;

    public AppointmentClientListDto() {
    }

    public AppointmentClientListDto(List<Appointment> appointments) {
        this.appointments = new ArrayList<>();

        for(Appointment appointment: appointments){
            this.appointments.add(new AppointmentClientDto(appointment));
        }
    }

    public List<AppointmentClientDto> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentClientDto> appointments) {
        this.appointments = appointments;
    }
}
