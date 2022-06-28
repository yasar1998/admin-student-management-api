package com.example.ASMapi.security.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class AuthenticationErrorResponse {
    String title = "UNAUTHENTICATED";
    String message = "authentication is required";
}
