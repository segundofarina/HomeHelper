package ar.edu.itba.paw.homehelper.api.providers.id;


import ar.edu.itba.paw.homehelper.dto.ProviderDto;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.model.SProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Locale;

@Path("/providers/{id}")
@Controller
public class IdProvidersController {

    @Autowired
    SProviderService sProviderService;

    @Context
    HttpServletRequest request;

    @Autowired
    private MessageSource messageSource;

    @GET
    @Path("/")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getProviderById(@PathParam("id") final int id) {
        Locale locale = request.getLocale();

        final SProvider provider = sProviderService.getServiceProviderWithUserId(id);

        if(provider == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new ProviderDto(provider,locale,messageSource)).build();
    }


}
