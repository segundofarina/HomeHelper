package ar.edu.itba.paw.homehelper.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class StatelessLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    /* Get token manager service */
    @Autowired
    TokenAuthenticationService tokenAuthenticationService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        /* Add token to the response header */
        tokenAuthenticationService.addAuthentication(response, authentication);
    }

}
