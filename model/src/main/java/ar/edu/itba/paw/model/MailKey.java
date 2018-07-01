package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "verifyUsers")
public class MailKey {

    @Id
    @Column(name = "userid")
    private int userId;

    @Column(name = "keyCode", length = 1000, nullable = false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MailKey)) return false;
        MailKey mailKey = (MailKey) o;
        return getUserId() == mailKey.getUserId() &&
                Objects.equals(getKey(), mailKey.getKey());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUserId(), getKey());
    }
}
