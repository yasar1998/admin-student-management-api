package com.example.ASMapi.security.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class AuthorizationErrorResponse {
    String title = "UNAUTHORIZED";
    String message = "user is not authorized to access the resource";
}
