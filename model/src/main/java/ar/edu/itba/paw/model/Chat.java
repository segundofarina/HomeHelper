package ar.edu.itba.paw.model;

import java.util.List;

public class Chat implements Comparable<Chat>{

    List<Message> messages;
    User green;
    User grey;

    public Chat(User green, User grey, List<Message> messages) {
        this.messages = messages;
        this.green = green;
        this.grey = grey;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public User getGreen() {
        return green;
    }

    public User getGrey() {
        return grey;
    }

    public String getPreview() {

        Message last = messages.get(messages.size() - 1);
        String messg = last.getMessage();
        if (messg.length() > 30) {
            messg = messg.substring(0, 29) + "...";
        }
        return messg;
    }

    @Override
    public int compareTo(Chat o) {
        Message o1 = this.messages.get(0);
        Message o2 = o.messages.get(0);
        return o1.compareTo(o2);
    }
}
