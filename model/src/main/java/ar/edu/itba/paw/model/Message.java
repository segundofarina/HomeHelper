package ar.edu.itba.paw.model;

import java.sql.Timestamp;

public class Message {

    int from;
    int to;
    int thread;
    Timestamp Date;
    String message;

    public Message(int from, int to, Timestamp date, String message) {
        this.from = from;
        this.to = to;
        Date = date;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getThread() {
        return thread;
    }

    public Timestamp getDate() {
        return Date;
    }
}
