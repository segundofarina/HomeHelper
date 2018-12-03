package ar.edu.itba.paw.homehelper.api.providers.id;


import ar.edu.itba.paw.homehelper.dto.ProviderDto;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.SProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/providers/{id}")
@Controller
public class IdProvidersController {

    @Autowired
    SProviderService sProviderService;

    @GET
    @Path("/")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getProviderById(@PathParam("id") final int id) {
        final SProvider provider = sProviderService.getServiceProviderWithUserId(id);

        if(provider == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new ProviderDto(provider)).build();
    }

//    @PUT
//    @Path("/")
//    @Produces(value = MediaType.APPLICATION_JSON)
//    public Response updateProvider(@PathParam("id") final int id){
//        final SProvider provider = sProviderService.up
//    }

}
