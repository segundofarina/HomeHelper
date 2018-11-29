package ar.edu.itba.paw.homehelper.api;

import ar.edu.itba.paw.homehelper.dto.AptitudeDto;
import ar.edu.itba.paw.interfaces.services.AptitudeService;
import ar.edu.itba.paw.model.Aptitude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;

@Path("/aptitudes")
@Controller
public class AptitudesController {
    @Autowired
    AptitudeService aptitudeService;

    @Context
    private UriInfo uriInfo;


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAptitude(@PathParam("id") final int id){
        final Optional<Aptitude> aptitude = aptitudeService.getAptitude(id);

        if(!aptitude.isPresent()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new AptitudeDto(aptitude.get())).build();
    }


    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeAptitude(@PathParam("id") final int id){
        final Optional<Aptitude> newAptitude = aptitudeService.getAptitude(id);

        if(!newAptitude.isPresent()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        boolean removed = aptitudeService.removeAptitude(id);

        if(!removed){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok().build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAptitude(final AptitudeDto aptitudeDTO){

       if(aptitudeDTO == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        final Aptitude newAptitude = aptitudeService.addAptitude(1,aptitudeDTO.getServiceType().getId(),aptitudeDTO.getDescription());

        if(newAptitude == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); // TODO:Internal Server Error?
        }

        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(aptitudeDTO.getId())).build();

       return Response.created(uri).build();
    }
}
