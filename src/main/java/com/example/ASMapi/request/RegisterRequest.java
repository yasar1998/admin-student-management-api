package com.example.ASMapi.request;

import com.example.ASMapi.validation.NotAdmin;
import com.example.ASMapi.validation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "username cannot be empty")
    @UniqueUsername(message = "username has been taken")
    public String username;

    @NotBlank(message = "password cannot be empty")
    public String password;

    @NotBlank(message = "first name cannot be empty")
    public String firstName;

    @NotBlank(message = "last name cannot be empty")
    public String lastName;

    @NotAdmin(message = "admin registration is impossible")
    public String roles;

}
