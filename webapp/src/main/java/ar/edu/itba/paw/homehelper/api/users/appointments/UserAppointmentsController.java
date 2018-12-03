package ar.edu.itba.paw.homehelper.api.users.appointments;

import ar.edu.itba.paw.homehelper.dto.AppointmentDto;
import ar.edu.itba.paw.homehelper.dto.AppointmentListDto;
import ar.edu.itba.paw.interfaces.services.AppointmentService;
import ar.edu.itba.paw.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;


@Path("/users/appointments")
@Controller
public class UserAppointmentsController {

    @Autowired
    AppointmentService appointmentService;
    @Context
    private UriInfo uriInfo;


    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppointments() {
        List<Appointment> appointments = appointmentService.getAppointmentsByUserId(2);

        if(appointments == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new AppointmentListDto(appointments)).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAppointment(final AppointmentDto appointmentDTO) {

        if(appointmentDTO == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Appointment newAppointment = appointmentService.addAppointment(1,appointmentDTO.getProvider().getId(),
                appointmentDTO.getServiceType().getId(),appointmentDTO.getDate(),appointmentDTO.getAddress(),appointmentDTO.getDescription());

        if(newAppointment == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); // TODO:Internal Server Error?
        }

        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newAppointment.getAppointmentId())).build();

        return Response.created(uri).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppointment(@PathParam("id") final int id) {
        Appointment appointment = appointmentService.getAppointment(id);

        if(appointment == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new AppointmentDto(appointment)).build();
    }


}
