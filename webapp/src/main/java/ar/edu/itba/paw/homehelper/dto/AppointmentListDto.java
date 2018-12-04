//package ar.edu.itba.paw.homehelper.dto;
//
//import ar.edu.itba.paw.model.Appointment;
//import org.springframework.context.MessageSource;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//public class AppointmentListDto {
//    private List<AppointmentDto> appointments;
//
//    public AppointmentListDto() {
//    }
//
//    public AppointmentListDto(List<Appointment> appointments, Locale locale, MessageSource messageSource) {
//        this.appointments = new ArrayList<>();
//
//        for(Appointment appointment: appointments){
//            this.appointments.add(new AppointmentDto(appointment,locale, messageSource));
//        }
//    }
//
//    public List<AppointmentDto> getAppointments() {
//        return appointments;
//    }
//
//    public void setAppointments(List<AppointmentDto> appointments) {
//        this.appointments = appointments;
//    }
//}
