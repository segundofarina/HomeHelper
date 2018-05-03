package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.ChatDao;
import ar.edu.itba.paw.interfaces.ChatService;
import ar.edu.itba.paw.model.Chat;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ChatServiceImpl implements ChatService{

    @Autowired
    ChatDao chatDao;
    @Override
    public boolean sendMsg(int from,int to, String msg) {
       return chatDao.writeMessage(from,to,msg).isPresent();
    }

    @Override
    public List<Chat> getChatsOf(int userId) {
        return chatDao.getChatsOf(userId);
    }
}
