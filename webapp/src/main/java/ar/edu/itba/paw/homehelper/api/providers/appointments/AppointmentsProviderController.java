package ar.edu.itba.paw.homehelper.api.providers.appointments;

import ar.edu.itba.paw.homehelper.dto.ActionDto;
import ar.edu.itba.paw.homehelper.dto.AppointmentDto;
import ar.edu.itba.paw.homehelper.dto.AppointmentListDto;
import ar.edu.itba.paw.homehelper.utils.LoggedUser;
import ar.edu.itba.paw.interfaces.services.AppointmentService;
import ar.edu.itba.paw.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/providers/appointments")
@Controller
public class AppointmentsProviderController {

    //int loggedInProvider=1;

    @Autowired
    LoggedUser loggedUser;

    @Autowired
    AppointmentService appointmentService;

    @GET
    @Path("/")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getProviderAppointments(){

//          TODO: Use spring security Roles
//        if(!loggedUser.isProvider()){
//            return Response.status(Response.Status.FORBIDDEN).build();
//        }
        List<Appointment> list = appointmentService.getAppointmentsByProviderId(loggedUser.id());
        return Response.ok(new AppointmentListDto(list)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProviderAppointment(@PathParam("id") final Integer id){
        Appointment ap = appointmentService.getAppointment(id);
        if(ap == null || ap.getProvider().getId() != loggedUser.id()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new AppointmentDto(ap)).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAppointment(@PathParam("id") final int id, final String action) {

        final Appointment appointment = appointmentService.getAppointment(id);

        if(appointment == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final boolean updateAppointment;

        ActionDto update = ActionDto.parse(action);

        if(update == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }else if(update.equals(ActionDto.CONFIRM)){
            updateAppointment = appointmentService.confirmAppointment(appointment.getAppointmentId()); //TODO check if logged in user is allowed to change the status
        }else if(update.equals(ActionDto.COMPLETE)){
            updateAppointment = appointmentService.completedAppointment(appointment.getAppointmentId());
        }else{
            updateAppointment = appointmentService.rejectAppointment(appointment.getAppointmentId());
        }

        if(!updateAppointment) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); //TODO check if it should return internal server error
        }

        return Response.ok().build();
    }
}



