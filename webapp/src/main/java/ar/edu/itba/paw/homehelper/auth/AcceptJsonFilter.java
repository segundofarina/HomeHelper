package ar.edu.itba.paw.homehelper.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Component
public class CustomAuthenticationManager extends UsernamePasswordAuthenticationFilter {

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
/*
    // Accept Content-type: application/json on login

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("ACAAAA");
        if(!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " +request.getMethod());
        }

        BufferedReader reader = null;
        try {
            reader = request.getReader();
            StringBuffer sb = new StringBuffer();
            String line = null;

            while((line = reader.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
            }

            String parsedReq = sb.toString();
            if(parsedReq != null) {
                ObjectMapper mapper = new ObjectMapper();
                AuthReq authReq = mapper.readValue(parsedReq, AuthReq.class);
                return new UsernamePasswordAuthenticationToken(authReq.username, authReq.password);
            }
        } catch (IOException e) {
            // Nothing
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                // Nothing
            }
        }

        return super.attemptAuthentication(request, response);
    }

    public static class AuthReq {
        String username;
        String password;
    }*/

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        //return super.obtainUsername(request);
        return "mlopez";
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        //return super.obtainPassword(request);
        return "1234";
    }

    //@Override
    //protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        //if(request.getHeader("X-Authorization") == null) {
        //    return true;
        //}
        //return false;
    //}
}
