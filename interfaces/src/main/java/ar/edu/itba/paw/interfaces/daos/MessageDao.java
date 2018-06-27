package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.Chat;
import ar.edu.itba.paw.model.Message;

import java.util.List;
import java.util.Optional;

public interface MessageDao {

    boolean sendMessageToProvider(int userId, int providerId, String message);

    boolean sendMessageToUser(int providerId, int userId, String message);

    List<Chat> getChatsOfProvider(int providerId);

    List<Chat> getChatsOfUser(int userId);

    boolean markAsRead(Chat chat);
}
