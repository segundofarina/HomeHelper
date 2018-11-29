package ar.edu.itba.paw.homehelper.api;

import ar.edu.itba.paw.interfaces.services.CoordenatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/coordenates")
@Controller
public class CoordenatesController {
    @Autowired
    CoordenatesService coordenatesService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCoordenates(){
        return null;
    }

}
