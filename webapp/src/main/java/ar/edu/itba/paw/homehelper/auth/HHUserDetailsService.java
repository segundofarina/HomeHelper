package ar.edu.itba.paw.homehelper.auth;


import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
public class HHUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService us;

    @Autowired
    private SProviderService sp;

    private static final Logger LOGGER = LoggerFactory.getLogger(HHUserDetailsService.class);

    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = us.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("No user by the name " + username));



        boolean isVerified = false;
        boolean isProvider = false;
        int userId = user.getId();
        String firstName = user.getFirstname();
        String lastName = user.getLastname();
        String imgUrl = "";

        final ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

        if (user.isVerified()) {
            LOGGER.info("{} is verified", user.getUsername());
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            isVerified = true;
        } else {
            LOGGER.info("{} is unverified", user.getUsername());
            authorities.add(new SimpleGrantedAuthority("ROLE_UNVERIFIED_USER"));
        }

        if (sp.isServiceProvider(user.getId())) {
            authorities.add((new SimpleGrantedAuthority("ROLE_PROVIDER")));
            isProvider = true;
        }

        LOGGER.info("{} logged in", user.getUsername());

        return new HHUserDetails(username, user.getPassword(), authorities, userId, firstName, lastName, isProvider, isVerified, imgUrl);
    }


    public HHUserDetails loadFromToken(final String username, final int id, final String firstName, final String lastName, final boolean isProvider, final boolean isVerified, final String urlImg) {
        final ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if(isVerified) {
            LOGGER.info("{} is verified", username);
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            LOGGER.info("{} is unverified", username);
            authorities.add(new SimpleGrantedAuthority("ROLE_UNVERIFIED_USER"));
        }

        if(isProvider) {
            authorities.add(new SimpleGrantedAuthority("ROLE_PROVIDER"));
        }

        return new HHUserDetails(username, "", authorities, id, firstName, lastName, isProvider, isVerified, urlImg);
    }


}
