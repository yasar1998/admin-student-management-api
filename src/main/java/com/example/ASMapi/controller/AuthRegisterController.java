package com.example.ASMapi.controller;

import com.example.ASMapi.dto.AppUserDto;
import com.example.ASMapi.request.LoginRequest;
import com.example.ASMapi.request.RegisterRequest;
import com.example.ASMapi.security.response.TokenResponse;
import com.example.ASMapi.security.utils.TokenManager;
import com.example.ASMapi.service.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@Slf4j
public class AuthRegisterController {
    private final AuthenticationManager authenticationManager;

    private final TokenManager tokenManager;

    private final TokenResponse tokenResponse;

    private final AppUserService appUserService;


    public AuthRegisterController(AuthenticationManager authenticationManager, TokenManager tokenManager, TokenResponse tokenResponse, AppUserService appUserService) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.tokenResponse = tokenResponse;
        this.appUserService = appUserService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest){

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        log.info("posting login data in controller...");
        tokenResponse.setToken(tokenManager.generateToken(loginRequest.getUsername()));
        return ResponseEntity.ok().body(tokenResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<AppUserDto> register(@Valid @RequestBody RegisterRequest registerRequest){
        return new ResponseEntity<>(appUserService.createRecord(registerRequest), HttpStatus.CREATED);
    }


}
