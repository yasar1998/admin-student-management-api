package com.example.ASMapi.controller;

import com.example.ASMapi.dto.AppUserDto;
import com.example.ASMapi.request.PasswordUpdateRequest;
import com.example.ASMapi.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/account")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/")
    public ResponseEntity<AppUserDto> getCurrentUser(HttpServletRequest request){
        return ResponseEntity.ok(appUserService.getCurrentRecord(request.getUserPrincipal().getName()));
    }

    @PutMapping("/password/update")
    public ResponseEntity<AppUserDto>updatePassword(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest, HttpServletRequest request){
        return new ResponseEntity<>(appUserService.updatePassword(passwordUpdateRequest, request.getUserPrincipal().getName()), HttpStatus.OK);
    }
}
