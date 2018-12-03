package ar.edu.itba.paw.homehelper.auth;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class TokenAuthenticationService {
    private final String TOKEN_HEADER = "X-Authorization";

    @Autowired
    TokenAuthenticationManager tokenAuthenticationManager;

    public void addAuthentication(final HttpServletResponse response, final Authentication authentication) {
        response.addHeader(TOKEN_HEADER, tokenAuthenticationManager.generateToken(authentication.getName()));
    }

    public Authentication getAuthentication(final HttpServletRequest request) {
        final String token = request.getHeader(TOKEN_HEADER);

        if(token != null && Jwts.parser().isSigned(token)) {
            return tokenAuthenticationManager.authenticate(new JwtAuthentication(token));
        }

        return null;
    }
}
