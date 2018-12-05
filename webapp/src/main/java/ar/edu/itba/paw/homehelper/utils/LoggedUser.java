package ar.edu.itba.paw.homehelper.utils;

import ar.edu.itba.paw.homehelper.auth.HHUserDetails;
import ar.edu.itba.paw.homehelper.auth.JwtAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoggedUser {
    public HHUserDetails getUserDetails() {
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthentication) {
            return (HHUserDetails) ((JwtAuthentication) SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        }
        return null;
    }

    public String username() {
        return getUserDetails().getUsername();
    }

    public Integer id() {
        if(getUserDetails() == null) {
            return null;
        }
        return getUserDetails().getId();
    }

    public Boolean isVerified() {
        if(getUserDetails() == null) {
            return null;
        }
        return getUserDetails().isVerified();
    }

    public Boolean isProvider() {
        if(getUserDetails() == null) {
            return null;
        }
        return getUserDetails().isProvider();
    }
}
