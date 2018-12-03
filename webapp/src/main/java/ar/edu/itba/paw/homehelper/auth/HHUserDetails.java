package ar.edu.itba.paw.homehelper.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.security.Principal;
import java.util.Collection;

public class HHUserDetails extends User implements Principal {
    private final int id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String imgUrl;
    private final boolean isProvider;
    private final boolean isVerified;

    public HHUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, int id, String firstName, String lastName, boolean isProvider, boolean isVerified, String imgUrl) {
        super(username, password, authorities);
        this.id = id;
        this.username = username;
        this.isProvider = isProvider;
        this.isVerified = isVerified;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imgUrl = imgUrl;
    }

    @Override
    public String getName() {
        return username;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public boolean isProvider() {
        return isProvider;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
