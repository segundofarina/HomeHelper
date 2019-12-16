package ar.edu.itba.paw.homehelper.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthentication extends AbstractAuthenticationToken {
    private String token;
    private HHUserDetails principal;

    public JwtAuthentication(String token) {
        this(token, null, null);
    }

    public JwtAuthentication(String token, HHUserDetails principal, Collection<GrantedAuthority> authorities) {
        super(authorities);
        this.token = token;
        this.principal = principal;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
