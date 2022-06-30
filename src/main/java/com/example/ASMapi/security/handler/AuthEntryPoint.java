package com.example.ASMapi.security.handler;

import com.example.ASMapi.security.response.AuthenticationErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    private final AuthenticationErrorResponse authenticationErrorResponse;

    public AuthEntryPoint(AuthenticationErrorResponse authenticationErrorResponse) {
        this.authenticationErrorResponse = authenticationErrorResponse;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(authenticationErrorResponse));

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
