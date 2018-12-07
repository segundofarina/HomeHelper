package ar.edu.itba.paw.homehelper.api.users;


import ar.edu.itba.paw.homehelper.dto.UserDto;
import ar.edu.itba.paw.homehelper.utils.LoggedUser;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;


@Path("/users")
@Controller
public class UsersController {

    @Context
    private UriInfo uriInfo;

    @Autowired
    UserService userService;

    @POST
    @Path("/")
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProvider(UserDto userDto){

       User user =userService.create(
               userDto.getUsername(),
               userDto.getPassword(),
               userDto.getFirstname(),
               userDto.getLastname(),
               userDto.getEmail(),
               userDto.getPhone(),
               userDto.getAddress(),
               null
               ); //TODO take out image of service

        if(user == null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getId())).build();
        return Response.created(uri).build();

    }
}
