package com.example.ASMapi.security.handler;

import com.example.ASMapi.security.response.AuthorizationErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthorizedResourceHandler implements AccessDeniedHandler {
    private final AuthorizationErrorResponse authorizationErrorResponse;

    public AuthorizedResourceHandler(AuthorizationErrorResponse authorizationErrorResponse) {
        this.authorizationErrorResponse = authorizationErrorResponse;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(authorizationErrorResponse));

        response.setStatus(HttpStatus.FORBIDDEN.value());
    }
}
