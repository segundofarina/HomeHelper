package ar.edu.itba.paw.homehelper.api.providers.appointments;

import ar.edu.itba.paw.homehelper.dto.ActionDto;
import ar.edu.itba.paw.homehelper.dto.AppointmentProviderDto;
import ar.edu.itba.paw.homehelper.dto.AppointmentProviderListDto;
import ar.edu.itba.paw.homehelper.utils.LoggedUser;
import ar.edu.itba.paw.interfaces.services.AppointmentService;
import ar.edu.itba.paw.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Locale;

@Path("/providers/appointments")
@Controller
public class AppointmentsProviderController {

    @Autowired
    LoggedUser loggedUser;

    @Autowired
    AppointmentService appointmentService;

    @Context
    HttpServletRequest request;

    @Autowired
    private MessageSource messageSource;

    @GET
    @Path("/")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getProviderAppointments(){

        Locale locale = request.getLocale();

        List<Appointment> list = appointmentService.getAppointmentsByProviderId(loggedUser.id());

        if(list == null){
            Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        }

        return Response.ok(new AppointmentProviderListDto(list,locale,messageSource)).build();

    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProviderAppointment(@PathParam("id") final Integer id){

        Locale locale = request.getLocale();

        Appointment ap = appointmentService.getAppointment(id);
        if(ap == null || ap.getProvider().getId() != loggedUser.id()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new AppointmentProviderDto(ap,locale,messageSource)).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAppointment(@PathParam("id") final Integer id, final ActionDto update) {

        if(id == null){

            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final Appointment appointment = appointmentService.getAppointment(id);

        if(appointment == null ){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(appointment.getClient().getId() != loggedUser.id()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        final boolean updateAppointment;

        if(update == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }else if(update.getAction().equals("confirm")){
            updateAppointment = appointmentService.confirmAppointment(appointment.getAppointmentId());
        }else if(update.getAction().equals("complete")){
            updateAppointment = appointmentService.completedAppointment(appointment.getAppointmentId());
        }else if(update.getAction().equals("reject")){
            updateAppointment = appointmentService.rejectAppointment(appointment.getAppointmentId());
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if(!updateAppointment) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok().build();
    }
}



