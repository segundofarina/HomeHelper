package ar.edu.itba.paw.homehelper.api.providers.appointments;

import ar.edu.itba.paw.homehelper.dto.ActionDto;
import ar.edu.itba.paw.homehelper.dto.AppointmentClientDto;
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
import javax.ws.rs.core.UriInfo;
import java.net.URI;
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

    @Context
    private UriInfo uriInfo;

    @Autowired
    private MessageSource messageSource;

    @GET
    @Path("/")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getProviderAppointments(){

        Locale locale = request.getLocale();

        List<Appointment> list = appointmentService.getAppointmentsByProviderId(loggedUser.id().orElseThrow(IllegalArgumentException::new));

        return Response.ok(new AppointmentProviderListDto(list,locale,messageSource,uriInfo.getBaseUri().getRawPath())).build();

    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProviderAppointment(@PathParam("id") final Integer id){

        Locale locale = request.getLocale();

        Appointment ap = appointmentService.getAppointment(id);
        if(ap == null || ap.getProvider().getId() != loggedUser.id().orElseThrow(IllegalArgumentException::new)){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new AppointmentProviderDto(ap,locale,messageSource)).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAppointment(@PathParam("id") final Integer id, final ActionDto update) {
        Locale locale = request.getLocale();

        if(id == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final Appointment appointment = appointmentService.getAppointment(id);

        if(appointment == null ){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if( loggedUser.id().map(lid -> lid.equals(appointment.getClient().getId())).orElse(true)){
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

        Appointment updatedAppointment = appointmentService.getAppointment(id);

        return Response.ok(new AppointmentProviderDto(updatedAppointment, locale, messageSource)).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAppointment(final AppointmentClientDto appointmentDto) {

        if(appointmentDto.getServiceType() == null || appointmentDto.getDescription() == null || appointmentDto.getDate() == null || appointmentDto.getProvider() == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if(!loggedUser.id().isPresent()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if(loggedUser.id().get() == appointmentDto.getProvider().getId()){
            return Response.status(Response.Status.CONFLICT).build();
        }

        Appointment newAppointment = appointmentService.addAppointment(loggedUser.id().get(),appointmentDto.getProvider().getId(),appointmentDto.getServiceType().getId(),appointmentDto.getDate(),
                appointmentDto.getAddress(),appointmentDto.getDescription());

        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newAppointment.getAppointmentId())).build();

        return Response.created(uri).build();
    }

}



