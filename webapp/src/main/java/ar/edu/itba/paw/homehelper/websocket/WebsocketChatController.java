package ar.edu.itba.paw.homehelper.websocket;

import ar.edu.itba.paw.homehelper.auth.HHUserDetails;
import ar.edu.itba.paw.homehelper.auth.JwtAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WebsocketChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/messages")
    public void sendMsg(MsgEntity msg, Principal principal) {
        if(principal == null) {
            /* TODO: throw custom exception, handle exceptions in controller */
            throw new IllegalArgumentException();
        }

        /* Current user sendig the message */
        HHUserDetails user = (HHUserDetails) ((JwtAuthentication) principal).getPrincipal();
        System.out.println(user.getId());
        System.out.println(user.getUsername());
        //System.out.println(principal.getName());

        /* TODO: VALIDATE USERNAME */

        MsgEntity outputMsg = msg;
        outputMsg.setUsingAsClient(!msg.isUsingAsClient());

        messagingTemplate.convertAndSendToUser(msg.getUsername(), "/queue/messages", outputMsg);
    }
}
