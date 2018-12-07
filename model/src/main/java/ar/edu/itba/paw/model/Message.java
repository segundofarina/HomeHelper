package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message implements Comparable<Message>{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messages_msgId_seq")
    @SequenceGenerator(sequenceName = "messages_msgId_seq", name = "messages_msgId_seq", allocationSize = 1)
    @Column(name = "msgId")
    private int id;

    @Column(name = "userFrom")
    private int from;

    @Column(name = "userTo")
    private int to;

    @Transient
    private int thread;

    @Column(name = "providerId")
    private int providerId;

    @Column(name = "userId")
    private int userId;

    @Column(name = "read")
    private boolean read;

    @Column(name = "messageDate")
    private Date date;

    @Column(name = "message", length = 10000)
    private String message;

    /* packakge */ Message() {

    }

    public Message(int from, int to, Date date, int providerId, int userId, String message) {
        this.from = from;
        this.to = to;
        this.providerId = providerId;
        this.userId = userId;
        this.date = date;
        this.message = message;
        this.read = false;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
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

    public Date getDate() {
        return date;
    }

    public int getProviderId() {
        return providerId;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public int compareTo(Message o) {
        return o.getDate().compareTo(this.getDate());
    }
}
