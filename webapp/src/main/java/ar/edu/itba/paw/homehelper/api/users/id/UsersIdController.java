package ar.edu.itba.paw.homehelper.api.users.id;


import ar.edu.itba.paw.interfaces.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


@Path("/users/{id}")
@Controller
public class UsersIdController {

    @Autowired
    UserService userService;

    @Path("/image")
    @Produces({"image/png", "image/jpeg"})
    public Response getImage(@PathParam("id") Integer id){
        if(id == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return userService.getProfileImage(id)
                .map(im->{
                    System.out.println("GOT IMAGEE"+im.getImageId());
                    return Response.ok(im.getImage()).build();
                })
                .orElse(Response.status(Response.Status.REQUESTED_RANGE_NOT_SATISFIABLE).build());


    }

}
