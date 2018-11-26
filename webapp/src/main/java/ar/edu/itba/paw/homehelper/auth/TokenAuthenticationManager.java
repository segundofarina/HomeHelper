package ar.edu.itba.paw.homehelper.auth;

import ar.edu.itba.paw.homehelper.websocket.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

@Component
public class TokenAuthenticationManager implements AuthenticationManager {
    private final static SecretKey key = Keys.hmacShaKeyFor("uIasbJOJPXpR1z3lfkcge2Yl5BX8yLSamr13JJNEei-K2ZHaVT1xXW3Wl2G_bsquk-LtgWKrcx2unwn3WX0FocMP0YXAHqGqv6zcdbSBWv8Q_y_jEvFH8j9e2CJ32mc4PrIuOBCTkhpLjnMPq6M7Bd7uansrWwF3AhY4NzJQaX9M-yPx7lswFgN3W4q23kOEDOBGIIRX7faLI49QRpf-LIfufSkTay78FKogm_P7x36QjCM1AEfjQaU4UGxe9SWy1Z0lgawc07VqrzSAVMHIbutuefEG8Zch0gwe949BYXgtvSIIktPfdJdXNMTMMJtHKjW_YJgyrE2IrOiKg0PUww".getBytes());


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthentication token = (JwtAuthentication) authentication;
        String tokenString = (String) token.getCredentials();

        try {
            Jws<Claims> verifiedToken = Jwts.parser().setSigningKey(key).parseClaimsJws(tokenString);
            UserEntity userEntity = new UserEntity(Integer.parseInt(verifiedToken.getBody().getId()), verifiedToken.getBody().getSubject());
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            token = new JwtAuthentication(tokenString, userEntity, authorities);
            token.setAuthenticated(true);

        } catch (JwtException e) {
            throw new BadCredentialsException("Invalid Token");
        }

        return token;
    }

    private void generateToken() {
        String token = Jwts.builder().setSubject("tinchovictory3").setId("2").signWith(key).compact();
        System.out.println(token);

        // Generated token for subject tinchovictory is
        // eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aW5jaG92aWN0b3J5IiwianRpIjoiMSJ9.5jxlU2uCoV_xWl9IAL-CDPJePYUSmXe-CNlPifNUBU5b4guDWJT6eHCMKuXUdZp6AEQ4Tc0BQ-e6Hjg4DSiMXg

        // Generated token for subject tinchovictory2 is
        // eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aW5jaG92aWN0b3J5MiIsImp0aSI6IjIifQ.MkCY1izRB5ME2jid2Ftz6pzZNRhw8GhVhBUpCYKBOkXV8O_WAFbQKWD-JGPa1Csh_v92TKxQ-IY1k4kGGjBYrw

        // Generated token for subject tinchovictory3 is
        // eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aW5jaG92aWN0b3J5MyIsImp0aSI6IjIifQ.0qa8ryVqu0qlIc2NhAPYAxb9QWi4F0s6I4qPrl8WV9th209wvEt4QxSIvseXBu_FCzx6rAWiT1oC7qpe9oF24Q
    }
}
