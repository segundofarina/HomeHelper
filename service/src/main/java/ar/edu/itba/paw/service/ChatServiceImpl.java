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
public class ChatServiceImpl implements ChatService {

    @Autowired
    MessageDao messageDao;

    @Autowired
    ApplicationContext context;

    @Override
    public int getLastMsgThreadUser(int userId) {
        List<Chat> list = getChatsOfUser(userId);
        if (list.isEmpty()) {
            return -1;
        }
        return list.get(0).getGrey().getId();

    }

    @Override
    public int getLastMsgThreadProvider(int providerId) {
        List<Chat> list = getChatsOfProvider(providerId);
        if (list.isEmpty()) {
            return -1;
        }
        return list.get(0).getGrey().getId();

    }

    @Override
    public boolean sendAppointmentMsg(int from, int to, String date, String description) {
        String msg = null;
        if (description.isEmpty()) {
            //String locale = user.getLocale;
            //Arrays.stream(Locale.getAvailableLocales()).filter(l => l.equals(locale));
            msg = context.getMessage("defaultChatMsg", new Object[]{date}, Locale.getDefault());
        } else {
            msg = context.getMessage("defaultChatMsgDesc", new Object[]{date, description}, Locale.getDefault());
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
        if (message.isEmpty() || message.equals("")) {
            return false;
        }
        return messageDao.sendMessageToProvider(userId, providerId, message);
    }

    @Transactional
    @Override
    public boolean sendMessageToUser(int providerId, int userId, String message) {
        if (message.isEmpty() || message.equals("")) {
            return false;

        }
        return messageDao.sendMessageToUser(providerId, userId, message);
    }

    @Transactional
    @Override
    public List<Chat> getChatsOfProvider(int providerId) {
        List<Chat> chats = messageDao.getChatsOfProvider(providerId);
        Collections.sort(chats);
        return chats;
    }

    @Transactional
    @Override
    public List<Chat> getChatsOfUser(int userId) {
        List<Chat> chats = messageDao.getChatsOfUser(userId);
        Collections.sort(chats);
        return chats;
    }

    @Transactional
    @Override
    public boolean markAsRead(Chat chat) {
        return messageDao.markAsRead(chat);
    }

    @Override
    public Chat getChatOfUser(int userId, int providerId) {
        List<Chat> list = getChatsOfUser(userId);
        for (Chat c : list) {
            if (c.getGrey().getId() == providerId) {
                return c;
            }
        }
        return null;
    }

    @Override
    public Chat getChatOfProvider(int providerId, int userId) {
        List<Chat> list = getChatsOfProvider(providerId);
        for (Chat c : list) {
            if (c.getGrey().getId() == userId) {
                return c;
            }
        }
        return null;
    }
}
