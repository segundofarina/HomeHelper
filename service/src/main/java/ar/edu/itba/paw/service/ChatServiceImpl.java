package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.ChatDao;
import ar.edu.itba.paw.interfaces.ChatService;
import ar.edu.itba.paw.model.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Chat> chat = chatDao.getChatBetween(providerId,clientId);
        if(!chat.isPresent()){
            return null;
        }
        return chat.get();

    }

    @Override
    public int getLastMsgThread(int providerId) {
        List<Chat> list = getChatsOf(providerId);
        return list.get(list.size() - 1).getGrey().getId();
    }
}
