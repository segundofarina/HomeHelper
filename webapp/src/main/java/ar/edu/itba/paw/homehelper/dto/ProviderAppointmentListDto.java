package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class ProviderAppointmentListDto {
    private List<ProviderAppointmentDto> appointments;

    public ProviderAppointmentListDto() {
    }

    public ProviderAppointmentListDto(List<Appointment> appointments) {
        this.appointments = new ArrayList<>();

        for(Appointment appointment: appointments){
            this.appointments.add(new ProviderAppointmentDto(appointment));
        }
    }

    public List<ProviderAppointmentDto> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<ProviderAppointmentDto> appointments) {
        this.appointments = appointments;
    }
}
