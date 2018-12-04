package ar.edu.itba.paw.homehelper.api.providers.messages;

import ar.edu.itba.paw.homehelper.dto.ChatListDTO;
import ar.edu.itba.paw.homehelper.utils.LoggedUser;
import ar.edu.itba.paw.interfaces.services.ChatService;
import ar.edu.itba.paw.model.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/providers/messages")
@Controller
public class MessagesProviderController {

    @Autowired
    LoggedUser loggedUser;

    @Autowired
    private ChatService chatService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessages() {
        List<Chat> chatList = chatService.getChatsOfProvider(loggedUser.id());
        return Response.ok(new ChatListDTO(chatList)).build();
    }
}
