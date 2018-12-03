package ar.edu.itba.paw.homehelper.utils;

import ar.edu.itba.paw.homehelper.auth.HHUserDetails;
import ar.edu.itba.paw.homehelper.auth.JwtAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoggedUser {
    public HHUserDetails getUserDetails() {
        return (HHUserDetails) ((JwtAuthentication) SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
    }

    public String username() {
        return getUserDetails().getUsername();
    }

    public int id() {
        return getUserDetails().getId();
    }

    public boolean isVerified() {
        return getUserDetails().isVerified();
    }

    public boolean isProvider() {
        return getUserDetails().isProvider();
    }
}
