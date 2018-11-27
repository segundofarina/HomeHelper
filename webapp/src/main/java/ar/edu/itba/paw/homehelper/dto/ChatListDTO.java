package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Chat;

import java.util.LinkedList;
import java.util.List;

public class ChatListDTO {
    private List<ChatDTO> chats;

    public ChatListDTO() {
    }

    public ChatListDTO(List<Chat> chats) {
        this.chats = new LinkedList<>();
        for(Chat chat : chats) {
            this.chats.add(new ChatDTO(chat));
        }
    }

    public List<ChatDTO> getChats() {
        return chats;
    }

    public void setChats(List<ChatDTO> chats) {
        this.chats = chats;
    }
}
