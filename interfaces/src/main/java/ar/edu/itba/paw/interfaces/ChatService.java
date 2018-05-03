package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Chat;

import java.util.List;

public interface ChatService {

    boolean sendMsg(int from,int to, String msg);

    List<Chat> getChatsOf(int userId);
}
