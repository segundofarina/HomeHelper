package ar.edu.itba.paw.homehelper.auth;

import ar.edu.itba.paw.interfaces.controller.SecurityUserService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserServiceImpl implements SecurityUserService {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private HHUserDetailsService userDetailsService;

    public User getLoggedInUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null) {
            return null;
        }
        User user = userService.findByUsername(authentication.getName());

        if(user != null && user.isVerified() && authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_UNVERIFIED_USER"))) {
            /* Add roles */

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);


        }

        return user;

    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
