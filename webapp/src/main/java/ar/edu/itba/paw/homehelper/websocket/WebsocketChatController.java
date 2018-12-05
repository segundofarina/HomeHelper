package ar.edu.itba.paw.homehelper.websocket;

import ar.edu.itba.paw.homehelper.auth.HHUserDetails;
import ar.edu.itba.paw.homehelper.auth.JwtAuthentication;
import ar.edu.itba.paw.interfaces.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WebsocketChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/messages")
    public void sendMsg(MsgEntity msg, Principal principal) {
        if(principal == null) {
            /* TODO: throw custom exception, handle exceptions in controller */
            throw new IllegalArgumentException();
        }

        /* Current user sending the message */
        HHUserDetails user = (HHUserDetails) ((JwtAuthentication) principal).getPrincipal();
        //System.out.println(principal.getName());

        String toUsername = msg.getUsername();

        /* TODO: VALIDATE USERNAME */

        MsgEntity outputMsg = msg;
        outputMsg.setUsingAsClient(!msg.isUsingAsClient());
        outputMsg.setUsername(user.getUsername());

        messagingTemplate.convertAndSendToUser(toUsername, "/queue/messages", outputMsg);
    }
}
