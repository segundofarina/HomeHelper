package ar.edu.itba.paw.homehelper.api.users.messages;

import ar.edu.itba.paw.homehelper.dto.ChatListDTO;
import ar.edu.itba.paw.interfaces.services.ChatService;
import ar.edu.itba.paw.model.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("messages")
@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessages(@QueryParam("provider") final boolean usingAsProvider) {
        List<Chat> chatList;
        if(!usingAsProvider) {
            chatList = chatService.getChatsOfUser(2);
        } else {
            chatList = chatService.getChatsOfProvider(6);
        }

        System.out.println("using as provider" + usingAsProvider);

        return Response.ok(new ChatListDTO(chatList)).build();
    }
}
