package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.MessageDao;
import ar.edu.itba.paw.model.Chat;
import ar.edu.itba.paw.model.Message;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Instant;
import java.util.*;

@Repository
public class MessageHibernateDao implements MessageDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public boolean sendMessageToProvider(int userId, int providerId, String message) {
        Message mesg = new Message(userId, providerId, Date.from(Instant.now()), providerId, userId, message);
        em.persist(mesg);
        return true;
    }

    @Override
    public boolean sendMessageToUser(int providerId, int userId, String message) {
        Message mesg = new Message(providerId, userId, Date.from(Instant.now()), providerId, userId, message);
        em.persist(mesg);
        return true;
    }

    @Override
    public List<Chat> getChatsOfProvider(int providerId) {
        User provider = em.find(User.class, providerId);
        if (provider == null) {
            return Collections.EMPTY_LIST;
        }
        List<Message> messages = em.createQuery("SELECT m FROM Message m where m.providerId = :providerId", Message.class)
                .setParameter("providerId", providerId)
                .getResultList();

        HashMap<Integer, Chat> chatHashMap = new HashMap<>();

        for (Message m : messages) {
            if (chatHashMap.containsKey(m.getUserId())) {
                chatHashMap
                        .get(m.getUserId())
                        .getMessages()
                        .add(m);
            } else {
                User user = em.find(User.class, m.getUserId());
                Chat newChat = new Chat(provider, user, new ArrayList<>());
                newChat
                        .getMessages()
                        .add(m);

                chatHashMap.put(m.getUserId(), newChat);
            }
        }
        return new ArrayList<>(chatHashMap.values());
    }


    @Override
    public List<Chat> getChatsOfUser(int userId) {
        User user = em.find(User.class, userId);
        if (user == null) {
            return Collections.EMPTY_LIST;
        }
        List<Message> messages = em.createQuery("SELECT m FROM Message m where m.userId = :userId", Message.class)
                .setParameter("userId", userId)
                .getResultList();
        HashMap<Integer, Chat> chatHashMap = new HashMap<>();

        for (Message m : messages) {
            if (chatHashMap.containsKey(m.getProviderId())) {
                chatHashMap
                        .get(m.getProviderId())
                        .getMessages()
                        .add(m);
            } else {
                User provider = em.find(User.class, m.getProviderId());
                Chat newChat = new Chat(user, provider, new ArrayList<>());

                newChat
                        .getMessages()
                        .add(m);

                chatHashMap.put(m.getProviderId(), newChat);
            }
        }
        return new ArrayList<>(chatHashMap.values());
    }


    @Override
    public boolean markAsRead(Chat chat) {
        for (Message m : chat.getMessages()) {
            m.setRead(true);
        }
        return true;
    }
}
