package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.Chat;
import ar.edu.itba.paw.model.Message;
import ar.edu.itba.paw.model.User;

import java.util.LinkedList;
import java.util.List;

public class ChatDTO {
    private int chatId;
    private String toName;
    private String toUsername;
    private List<MessageDTO> messages;
    private String toPictureUrl;

    public ChatDTO() {
    }

    public ChatDTO(Chat chat) {
        User toUser = chat.getGrey();
        this.chatId = toUser.getId();
        this.toName = toUser.getFirstname() + " " + toUser.getLastname();
        this.toUsername = toUser.getUsername();
        this.messages = new LinkedList<>();
        this.toPictureUrl = "http://pawserver.it.itba.edu.ar/paw-2018a-4/api/"+"users/"+this.chatId+"/image";

        for(Message message : chat.getMessages()) {
            String from = message.getFrom() == toUser.getId() ? "yours" : "mine";
            this.messages.add(new MessageDTO(from, message.getMessage()));
        }
    }

    public String getToPictureUrl() {
        return toPictureUrl;
    }

    public void setToPictureUrl(String toPictureUrl) {
        this.toPictureUrl = toPictureUrl;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }
}
