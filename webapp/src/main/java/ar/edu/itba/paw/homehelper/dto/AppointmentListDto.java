package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentListDto {
    private List<AppointmentDto> appointments;

    public AppointmentListDto() {
    }

    public AppointmentListDto(List<Appointment> appointments) {
        this.appointments = new ArrayList<>();

        for(Appointment appointment: appointments){
            this.appointments.add(new AppointmentDto(appointment));
        }
    }

    public List<AppointmentDto> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentDto> appointments) {
        this.appointments = appointments;
    }
}
