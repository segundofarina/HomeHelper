package ar.edu.itba.paw.homehelper.auth;

import ar.edu.itba.paw.interfaces.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class ValidUsersId {
    @Autowired
    private UserService userService;

    public boolean checkUserId(Authentication authentication, int id) {
        String username = authentication.getName();

        return userService.isValidUser(id, username);
    }
}
