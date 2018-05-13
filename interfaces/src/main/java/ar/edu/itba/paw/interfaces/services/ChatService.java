package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Chat;

import java.util.List;

public interface ChatService {

    boolean sendMsg(int from,int to, String msg);

    List<Chat> getChatsOf(int userId);

    Chat getChat(int greenId, int greyId);

    int getLastMsgThread(int providerId);

    boolean sendAppointmentMsg(int from, int to);
}
