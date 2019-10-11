package com.crypto.handler;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationHandler extends ForwardAuthenticationFailureHandler {
    private static final String URL = "/accountDisabled";

    public AuthenticationHandler() {
        this("/");
    }

    public AuthenticationHandler(String forwardUrl) {
        super(forwardUrl);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof DisabledException) {
            request.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception);
            request.getRequestDispatcher(URL).forward(request, response);
        }
    }
}
