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
        final User user = us.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user by the name " + username);
        }
        final ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if(sp.isServiceProvider(user.getId())){
            authorities.add((new SimpleGrantedAuthority("ROLE_PROVIDER")));
        }

        LOGGER.info("{} logged in", user.getUsername());

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    }


}
