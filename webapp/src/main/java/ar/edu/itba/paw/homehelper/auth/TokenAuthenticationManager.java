package ar.edu.itba.paw.homehelper.auth;

import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class TokenAuthenticationManager implements AuthenticationManager {
    private final static SecretKey key = Keys.hmacShaKeyFor("uIasbJOJPXpR1z3lfkcge2Yl5BX8yLSamr13JJNEei-K2ZHaVT1xXW3Wl2G_bsquk-LtgWKrcx2unwn3WX0FocMP0YXAHqGqv6zcdbSBWv8Q_y_jEvFH8j9e2CJ32mc4PrIuOBCTkhpLjnMPq6M7Bd7uansrWwF3AhY4NzJQaX9M-yPx7lswFgN3W4q23kOEDOBGIIRX7faLI49QRpf-LIfufSkTay78FKogm_P7x36QjCM1AEfjQaU4UGxe9SWy1Z0lgawc07VqrzSAVMHIbutuefEG8Zch0gwe949BYXgtvSIIktPfdJdXNMTMMJtHKjW_YJgyrE2IrOiKg0PUww".getBytes());

    @Autowired
    HHUserDetailsService userDetailsService;

    @Autowired
    UserService userService;

    @Autowired
    SProviderService providerService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthentication token = (JwtAuthentication) authentication;
        String tokenString = (String) token.getCredentials();

        try {
            Jws<Claims> verifiedToken = Jwts.parser().setSigningKey(key).parseClaimsJws(tokenString);
            System.out.println(verifiedToken);

            HHUserDetails userDetails = getUserDetailFromToken(verifiedToken.getBody());

            token = new JwtAuthentication(tokenString, userDetails, userDetails.getAuthorities());
            token.setAuthenticated(true);

        } catch (JwtException e) {
            throw new BadCredentialsException("Invalid Token");
        }

        return token;
    }

    public String generateToken(String username) {
        User user = userService.findByUsername(username);

        if(user == null) {
            throw new AccessDeniedException("User not found");
        }

        return Jwts.builder()
                        .setId( String.valueOf(user.getId()) )
                        .setSubject(username)
                        .claim("isVerified", String.valueOf(user.isVerified()))
                        .claim("isProvider", String.valueOf(providerService.isServiceProvider(user.getId())))
                    .signWith(key)
                    .compact();

    }

    private HHUserDetails getUserDetailFromToken(Claims claims) {
        final int id = Integer.parseInt(claims.getId());
        final String username = claims.getSubject();

        final boolean isVerified = (boolean) claims.get("isVerified");
        final boolean isProvider = (boolean) claims.get("isProvider");

        return userDetailsService.loadFromToken(username, id, isProvider, isVerified);
    }

}
