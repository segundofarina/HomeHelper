package ar.edu.itba.paw.homehelper.websocket;

import java.io.Serializable;

public class MsgEntity {
    private String username;
    private String text;

    public MsgEntity() {

    }

    public MsgEntity(String username, String text) {
        this.username = username;
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
