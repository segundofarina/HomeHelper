package ar.edu.itba.paw.homehelper.utils;

import ar.edu.itba.paw.homehelper.auth.HHUserDetails;
import ar.edu.itba.paw.homehelper.auth.JwtAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

    public Optional<Integer> id() {
        return Optional.ofNullable(getUserDetails()).map(HHUserDetails::getId);
    }

    public Optional<Boolean> isVerified() {

        return Optional.ofNullable(getUserDetails()).map(HHUserDetails::isVerified);
    }

    public Optional<Boolean> isProvider() {

        return Optional.ofNullable(getUserDetails()).map(HHUserDetails::isProvider);
    }
}
