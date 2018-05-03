package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.ChatDao;
import ar.edu.itba.paw.interfaces.ChatService;
import ar.edu.itba.paw.model.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    ChatDao chatDao;
    @Override
    public boolean sendMsg(int from,int to, String msg) {
        if(msg.isEmpty() || msg.equals("")) {
            return false;
        }
        return chatDao.writeMessage(from,to,msg).isPresent();
    }

    @Override
    public List<Chat> getChatsOf(int userId) {
        return chatDao.getChatsOf(userId);
    }

    @Override
    public Chat getChat(int providerId, int clientId) {
        for(Chat chat: getChatsOf(providerId)) {
            if(chat.getTo().getId() == clientId) {
                return chat;
            }
        }
        return null;
    }

    @Override
    public int getLastMsgThread(int providerId) {
        List<Chat> list = getChatsOf(providerId);
        return list.get(list.size() - 1).getTo().getId();
    }
}
