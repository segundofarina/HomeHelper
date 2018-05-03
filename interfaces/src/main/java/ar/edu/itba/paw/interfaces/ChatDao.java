package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Chat;
import ar.edu.itba.paw.model.Message;

import java.util.List;
import java.util.Optional;

public interface ChatDao {

    Optional<Chat> getChatBetween(int idFrom,int idTo);

    List<Chat> getChatsOf(int userId);

    Optional<Message> writeMessage(int from, int to, String message);






}
