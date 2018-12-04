package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentProviderListDto {
    private List<AppointmentProviderDto> appointments;

    public AppointmentProviderListDto() {
    }

    public AppointmentProviderListDto(List<Appointment> appointments) {
        this.appointments = new ArrayList<>();

        for(Appointment appointment: appointments){
            this.appointments.add(new AppointmentProviderDto(appointment));
        }
    }

    public List<AppointmentProviderDto> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentProviderDto> appointments) {
        this.appointments = appointments;
    }
}
