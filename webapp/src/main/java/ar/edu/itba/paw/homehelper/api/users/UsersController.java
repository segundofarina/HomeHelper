package ar.edu.itba.paw.homehelper.api.users;


import ar.edu.itba.paw.homehelper.auth.TokenAuthenticationManager;
import ar.edu.itba.paw.homehelper.dto.UserDto;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    TokenAuthenticationManager tokenAuthenticationManager;


    @POST
    @Path("/")
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(UserDto userDto){

        String username = userDto.getUsername();

        if(userService.findByUsername(username).isPresent()){
            return Response.status(Response.Status.CONFLICT).build();
        }

       User user =userService.create(
               userDto.getUsername(),
               passwordEncoder.encode(userDto.getPassword()),
               userDto.getFirstname(),
               userDto.getLastname(),
               userDto.getEmail(),
               userDto.getPhone(),
               userDto.getAddress()
               );

        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getId())).build();
        return Response.created(uri).header("X-Authorization", tokenAuthenticationManager.generateToken(username)).build();

    }
}
