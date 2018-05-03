package ar.edu.itba.paw.model;

import java.util.List;

public class Chat {

    List<Message> messages;
    User from;
    User to;

    public Chat( User from, User to,List<Message> messages) {
        this.messages = messages;
        this.from = from;
        this.to = to;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public User getFrom() {
        return from;
    }

    public User getTo() {
        return to;
    }

    public String getPreview(){

        Message last = messages.get(messages.size()-1);
        String messg=last.message;
        if(messg.length()>30){
            messg = messg.substring(0,29)+"...";
        }
        return messg;
    }
}
