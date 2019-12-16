package ar.edu.itba.paw.homehelper.auth;

import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.model.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
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

            HHUserDetails userDetails = getUserDetailFromToken(verifiedToken.getBody());

            token = new JwtAuthentication(tokenString, userDetails, userDetails.getAuthorities());
            token.setAuthenticated(true);

        } catch (JwtException e) {
            throw new BadCredentialsException("Invalid Token");
        }

        return token;
    }

    public String generateToken(String username) {
        User user = userService.findByUsername(username).orElseThrow(()->  new AccessDeniedException("User not found"));

        return Jwts.builder()
                        .setId( String.valueOf(user.getId()) )
                        .setSubject(username)
                        .claim("firstName", user.getFirstname())
                        .claim("lastName", user.getLastname())
                        .claim("imgUrl", "http://pawserver.it.itba.edu.ar/paw-2018a-4/api/"+"users/"+user.getId()+"/image")
                        .claim("isVerified", String.valueOf(user.isVerified()))
                        .claim("isProvider", String.valueOf(providerService.isServiceProvider(user.getId())))
                    .signWith(key)
                    .compact();

    }

    private HHUserDetails getUserDetailFromToken(Claims claims) {
        final int id = Integer.parseInt(claims.getId());
        final String username = claims.getSubject();
        final String firstName = (String) claims.get("firstName");
        final String lastName = (String) claims.get("lastName");
        final String imgUrl = (String) claims.get("imgUrl");

        final boolean isVerified = Boolean.parseBoolean((String) claims.get("isVerified"));
        final boolean isProvider = Boolean.parseBoolean((String) claims.get("isProvider"));

        return userDetailsService.loadFromToken(username, id, firstName, lastName, isProvider, isVerified, imgUrl);
    }

}
