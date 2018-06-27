package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.ChatDao;
import ar.edu.itba.paw.interfaces.daos.MessageDao;
import ar.edu.itba.paw.interfaces.services.ChatService;
import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Chat;
import ar.edu.itba.paw.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ChatServiceImpl implements ChatService{

    @Autowired
    MessageDao messageDao;

    @Autowired
    ApplicationContext context;

//    @Override
//    public boolean sendMsg(int from,int to, String msg) {
//        if(msg.isEmpty() || msg.equals("")) {
//            return false;
//        }
//        return messageDao.writeMessage(from,to,msg).isPresent();
//    }
//
//    @Override
//    public List<Chat> getChatsOf(int userId) {
//        List<Chat> chatList = chatDao.getChatsOf(userId);
//
//
//        Collections.sort(chatList, new Comparator<Chat>() {
//            @Override
//            public int compare(Chat o1, Chat o2) {
//                List<Message> listMsg1 = o1.getMessages();
//                List<Message> listMsg2 = o2.getMessages();
//
//                if(listMsg1.isEmpty() && listMsg2.isEmpty()) {
//                    return 0;
//                }
//
//                if(listMsg1.isEmpty()) {
//                    return 1;
//                }
//                if(listMsg2.isEmpty()) {
//                    return -1;
//                }
//
//                Message lastMsg1 = listMsg1.get(listMsg1.size() - 1);
//                Message lastMsg2 = listMsg2.get(listMsg2.size() - 1);
//
//                return lastMsg2.getDate().compareTo(lastMsg1.getDate());
//            }
//        });
//
//
//        return chatList;
//    }
//
//    @Override
//    public Chat getChat(int providerId, int clientId) {
//        Optional<Chat> chat = chatDao.getChatBetween(providerId,clientId);
//        if(!chat.isPresent()){
//            return null;
//        }
//        return chat.get();
//        //Optional.of(chat).map()
//
//    }

    @Override
    public int getLastMsgThreadUser(int userId) {
        List<Chat> list = getChatsOfUser(userId);
        if(list.isEmpty()) {
            return -1;
        }
        return list.get(0).getGrey().getId();

    }

    @Override
    public int getLastMsgThreadProvider(int providerId) {
        List<Chat> list = getChatsOfProvider(providerId);
        if(list.isEmpty()) {
            return -1;
        }
        return list.get(0).getGrey().getId();

    }

    @Override
    public boolean sendAppointmentMsg(int from, int to, String date, String description) {
        String msg = null;
        if(description.isEmpty()) {
            //String locale = user.getLocale;
            //Arrays.stream(Locale.getAvailableLocales()).filter(l => l.equals(locale));
            msg = context.getMessage("defaultChatMsg", new Object[] {date}, Locale.getDefault());
        } else {
            msg = context.getMessage("defaultChatMsgDesc", new Object[] {date, description}, Locale.getDefault());
        }
        return sendMessageToProvider(from, to, msg);
    }

    @Override
    public List<Chat> getLatestChatsOfProvider(int providerId) {
        final List<Chat> chats = getChatsOfProvider(providerId);
        int start = 0, end = chats.size() >= 4 ? 4 : chats.size();
        return chats.subList(start, end);
    }

    @Transactional
    @Override
    public boolean sendMessageToProvider(int userId, int providerId, String message) {
        if(message.isEmpty() || message.equals("")) {
            return false;
        }
        return messageDao.sendMessageToProvider(userId,providerId,message);
    }

    @Transactional
    @Override
    public boolean sendMessageToUser(int providerId, int userId, String message) {
            if(message.isEmpty() || message.equals("")) {
                return false;

        }
        return messageDao.sendMessageToUser(providerId,userId,message);
    }

    @Transactional
    @Override
    public List<Chat> getChatsOfProvider(int providerId) {
        return messageDao.getChatsOfProvider(providerId);
    }

    @Transactional
    @Override
    public List<Chat> getChatsOfUser(int userId) {
        return messageDao.getChatsOfUser(userId);
    }

    @Transactional
    @Override
    public boolean markAsRead(Chat chat) {
        return messageDao.markAsRead(chat);
    }

    @Override
    public Chat getChatOfUser(int userId, int providerId){
        List<Chat> list = getChatsOfUser(userId);
        for(Chat c : list){
            if(c.getGrey().getId() == providerId){
                return c;
            }
        }
        return null;
    }

    @Override
    public Chat getChatOfProvider(int providerId, int userId){
        List<Chat> list = getChatsOfProvider(providerId);
        for(Chat c : list){
            if(c.getGrey().getId() == userId){
                return c;
            }
        }
        return null;
    }
}
