package com.example.ASMapi.controller;

import com.example.ASMapi.dto.AppUserDto;
import com.example.ASMapi.service.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/account")
    public ResponseEntity<AppUserDto> getCurrentUser(HttpServletRequest request){
        return ResponseEntity.ok(appUserService.getCurrentRecord(request.getUserPrincipal().getName()));
    }

}
