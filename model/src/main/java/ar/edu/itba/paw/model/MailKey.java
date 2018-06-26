package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "verifyUsers")
public class MailKey {

    @Id
    @Column(name = "userid")
    private int userId;

    @Column(name = "keyCode",length = 1000, nullable = false)
    private String key;

    public MailKey(int userId, String key) {
        this.userId = userId;
        this.key = key;
    }

    public MailKey() {
    }

    public int getUserId() {
        return userId;
    }

    public String getKey() {
        return key;
    }
}
