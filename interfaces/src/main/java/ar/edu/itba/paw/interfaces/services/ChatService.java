package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Chat;

import java.util.List;

public interface ChatService {
    /*
    boolean sendMsg(int from,int to, String msg);

    List<Chat> getChatsOf(int userId);

    Chat getChat(int greenId, int greyId);

    int getLastMsgThread(int providerId);

    boolean sendAppointmentMsg(int from, int to, String date, String description);

    List<Chat> getLatestChatsOf(int providerId);

    */
    boolean sendMessageToProvider(int userId, int providerId, String message);

    boolean sendMessageToUser(int providerId, int userId, String message);

    List<Chat> getChatsOfProvider(int providerId);

    List<Chat> getChatsOfUser(int userId);

    boolean markAsRead(Chat chat);

    boolean sendAppointmentMsg(int from, int to, String date, String description);

    int getLastMsgThreadUser(int userId);

    Chat getChatOfUser(int userId, int providerId);

    List<Chat> getLatestChatsOfProvider(int providerId);

    int getLastMsgThreadProvider(int providerId);

    Chat getChatOfProvider(int providerId, int userId);

}
