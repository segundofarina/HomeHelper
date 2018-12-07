package ar.edu.itba.paw.homehelper.websocket;

import ar.edu.itba.paw.homehelper.auth.HHUserDetails;
import ar.edu.itba.paw.homehelper.auth.JwtAuthentication;
import ar.edu.itba.paw.interfaces.services.ChatService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.model.Chat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final static Logger LOGGER = LoggerFactory.getLogger(WebsocketChatController.class);
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

        int toId= userService.findByUsername(toUsername).orElseThrow(IllegalArgumentException::new).getId();
        if(msg.isUsingAsClient()){
            chatService.sendMessageToProvider(user.getId(),toId,msg.getText());
        }else{
            chatService.sendMessageToUser(user.getId(),toId,msg.getText());
        }


        MsgEntity outputMsg = msg;
        outputMsg.setUsingAsClient(!msg.isUsingAsClient());
        outputMsg.setUsername(user.getUsername());

        messagingTemplate.convertAndSendToUser(toUsername, "/queue/messages", outputMsg);
    }
}
