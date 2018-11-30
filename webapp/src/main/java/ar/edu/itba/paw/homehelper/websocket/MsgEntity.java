package ar.edu.itba.paw.homehelper.websocket;

import java.io.Serializable;

public class MsgEntity {
    private String username;
    private String text;
    private boolean usingAsClient;

    public MsgEntity() {

    }

    public MsgEntity(String username, String text, boolean usingAsClient) {
        this.username = username;
        this.text = text;
        this.usingAsClient = usingAsClient;
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

    public boolean isUsingAsClient() {
        return usingAsClient;
    }

    public void setUsingAsClient(boolean usingAsClient) {
        this.usingAsClient = usingAsClient;
    }
}
