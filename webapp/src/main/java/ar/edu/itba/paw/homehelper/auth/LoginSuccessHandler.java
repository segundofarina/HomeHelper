package ar.edu.itba.paw.homehelper.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.servlet.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public LoginSuccessHandler(String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        HttpSession session = request.getSession();

        /* Avoid showing page to unverified users */
        if(isUnverifiedUser(authentication)) {
            getRedirectStrategy().sendRedirect(request, response, "/unverified/user");
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }



        if(session != null) {
            String redirectUrl = (String) session.getAttribute("url_prior_login");

            if(redirectUrl != null) {
                session.removeAttribute("url_prior_login");
                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
                return;
            }
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private boolean isUnverifiedUser(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_UNVERIFIED_USER"));
    }
}
