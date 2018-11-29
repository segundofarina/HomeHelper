package ar.edu.itba.paw.homehelper.api;
import ar.edu.itba.paw.homehelper.dto.AppointmentDto;
import ar.edu.itba.paw.homehelper.dto.AppointmentListDto;
import ar.edu.itba.paw.homehelper.dto.ReviewDto;
import ar.edu.itba.paw.interfaces.services.AppointmentService;
import ar.edu.itba.paw.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/appointments")
@Controller
public class AppointmentsController {
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

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAppointment(final AppointmentDto appointmentDTO) {

       /* if(appointmentDTO == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Appointment newAppointment = appointmentService.addAppointment(appointmentDTO.getClient().getId(),appointmentDTO.getProvider().getId(),
                appointmentDTO.getServiceTtype().getId(),appointmentDTO.getDate().toString(),appointmentDTO.getAddress(),appointmentDTO.getJobDescription());

        if(newAppointment == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); // TODO:Internal Server Error?
        }

        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newAppointment.getAppointmentId())).build();

        return Response.created(uri).build();*/
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAppointment(@PathParam("id") final int id, final String action) {

       /* final Appointment newAppointment = appointmentService.getAppointment(id);

        if(newAppointment == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final boolean updateAppointment;

        ActionDto update = ActionDto.parse(action);

        if(update == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }else if(update.equals(ActionDto.CONFIRM)){
            updateAppointment = appointmentService.confirmAppointment(newAppointment.getAppointmentId());
        }else if(update.equals(ActionDto.COMPLETE)){
            updateAppointment = appointmentService.completedAppointment(newAppointment.getAppointmentId());
        }else{
            updateAppointment = appointmentService.rejectAppointment(newAppointment.getAppointmentId());
        }

        if(!updateAppointment) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }*/

        return Response.ok().build();
    }

    @POST
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response reviewAppointment(@PathParam("id") final int id,final ReviewDto reviewDTO){
/*
        final Appointment newAppointment = appointmentService.getAppointment(id);

        if(newAppointment == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if(newAppointment.isClientReview() || reviewDTO == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        appointmentService.reviewAppointment(newAppointment.getAppointmentId(),reviewDTO.getUser().getId(),reviewDTO.getAptitude().getId(),(int) reviewDTO.getCalificationDto().getQuality(),
                (int) reviewDTO.getCalificationDto().getCleanness(),(int) reviewDTO.getCalificationDto().getPrice(),(int) reviewDTO.getCalificationDto().getPunctuality(),
                (int) reviewDTO.getCalificationDto().getTreatment(),reviewDTO.getComment());
 */
        return Response.ok().build();
    }

}
