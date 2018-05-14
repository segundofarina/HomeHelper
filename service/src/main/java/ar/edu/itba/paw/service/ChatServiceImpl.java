package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.ChatDao;
import ar.edu.itba.paw.interfaces.services.ChatService;
import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Chat;
import ar.edu.itba.paw.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    ChatDao chatDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatServiceImpl.class);

    @Override
    public boolean sendMsg(int from,int to, String msg) {
        if(msg.isEmpty() || msg.equals("")) {
            return false;
        }
        return chatDao.writeMessage(from,to,msg).isPresent();
    }

    @Override
    public List<Chat> getChatsOf(int userId) {
        List<Chat> chatList = chatDao.getChatsOf(userId);

        LOGGER.debug("[Method : getChatsOf()]chatLIst size:{}",chatList.size());

        System.out.println("[Method : getChatsOf()] chatLIst size:"+chatList.size());

        Collections.sort(chatList, new Comparator<Chat>() {
            @Override
            public int compare(Chat o1, Chat o2) {
                List<Message> listMsg1 = o1.getMessages();
                List<Message> listMsg2 = o2.getMessages();

                if(listMsg1.isEmpty() && listMsg2.isEmpty()) {
                    return 0;
                }

                if(listMsg1.isEmpty()) {
                    return 1;
                }
                if(listMsg2.isEmpty()) {
                    return -1;
                }

                Message lastMsg1 = listMsg1.get(listMsg1.size() - 1);
                Message lastMsg2 = listMsg2.get(listMsg2.size() - 1);

                return lastMsg2.getDate().compareTo(lastMsg1.getDate());
            }
        });

        LOGGER.debug("[Method : getChatsOf()]chatLIst sorted size:{}",chatList.size());

        System.out.println("[Method : getChatsOf()]chatLIst sorted size:"+chatList.size());

        return chatList;
    }

    @Override
    public Chat getChat(int providerId, int clientId) {
        Optional<Chat> chat = chatDao.getChatBetween(providerId,clientId);
        if(!chat.isPresent()){
            return null;
        }
        return chat.get();

    }

    @Override
    public int getLastMsgThread(int providerId) {
        List<Chat> list = getChatsOf(providerId);
        if(list.isEmpty()) {
            return -1;
        }
        return list.get(0).getGrey().getId();

    }

    @Override
    public boolean sendAppointmentMsg(int from, int to) {
        String msg = "New appointment request";
        return sendMsg(from, to, msg);
    }
}
