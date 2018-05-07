package ar.edu.itba.paw.homehelper.auth;


import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.model.User;
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

    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = us.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user by the name " + username);
        }
        final ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if(sp.getServiceProviderWithUserId(user.getId())!=null){
            authorities.add((new SimpleGrantedAuthority("ROLE_PROVIDER")));
        }

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    }


}
