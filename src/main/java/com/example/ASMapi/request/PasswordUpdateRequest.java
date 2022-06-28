package com.example.ASMapi.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class PasswordUpdateRequest {
    @NotBlank(message = "old password cannot be empty")
    private String oldPassword;

    @NotBlank(message = "new password cannot be empty")
    private String newPassword;
}
