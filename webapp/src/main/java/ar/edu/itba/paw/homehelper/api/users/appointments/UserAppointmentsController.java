package ar.edu.itba.paw.homehelper.api.users.appointments;

import ar.edu.itba.paw.homehelper.dto.AppointmentClientDto;
import ar.edu.itba.paw.homehelper.dto.AppointmentClientListDto;
import ar.edu.itba.paw.homehelper.utils.LoggedUser;
import ar.edu.itba.paw.interfaces.services.AppointmentService;
import ar.edu.itba.paw.interfaces.services.ChatService;
import ar.edu.itba.paw.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Locale;


@Path("/users/appointments")
@Controller
public class UserAppointmentsController {

    @Autowired
    AppointmentService appointmentService;

    @Context
    private UriInfo uriInfo;

    @Context
    HttpServletRequest request;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LoggedUser loggedUser;

    @Autowired
    private ChatService chatService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppointments() {

        Locale locale = request.getLocale();
        if(!loggedUser.id().isPresent()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        List<Appointment> appointments = appointmentService.getAppointmentsByUserId(loggedUser.id().get());

        if(appointments == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new AppointmentClientListDto(appointments,locale,messageSource)).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAppointment(final AppointmentClientDto appointmentDTO) {

        if(appointmentDTO == null || appointmentDTO.getProvider() == null || appointmentDTO.getServiceType() == null || appointmentDTO.getDate() == null || appointmentDTO.getDescription() == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if(!loggedUser.id().isPresent()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }


        final Appointment newAppointment = appointmentService.addAppointment(loggedUser.id().get(),appointmentDTO.getProvider().getId(),
                appointmentDTO.getServiceType().getId(),appointmentDTO.getDate(),appointmentDTO.getAddress(),appointmentDTO.getDescription());

        if(newAppointment == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        chatService.sendAppointmentMsg(loggedUser.id().orElseThrow(IllegalArgumentException::new) , appointmentDTO.getProvider().getId(), appointmentDTO.getDate(), appointmentDTO.getDescription());



        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newAppointment.getAppointmentId())).build();
        return Response.created(uri).build();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppointment(@PathParam("id") final int id) {

        Locale locale = request.getLocale();

        Appointment appointment = appointmentService.getAppointment(id);


        if(appointment == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(!loggedUser.id().isPresent() || appointment.getClient().getId() != loggedUser.id().get()){
            return Response.status(Response.Status.FORBIDDEN).build();
        }


        return Response.ok(new AppointmentClientDto(appointment,locale,messageSource)).build();

    }



}
