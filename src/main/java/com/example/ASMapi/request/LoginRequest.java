package com.example.ASMapi.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "username cannot be empty")
    private String username;

    @NotBlank(message = "password cannot be empty")
    private String password;

}
