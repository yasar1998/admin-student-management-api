package com.example.ASMapi.security.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class TokenResponse {
    public String token;
}
