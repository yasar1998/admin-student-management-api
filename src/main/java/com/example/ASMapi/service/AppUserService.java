package com.example.ASMapi.service;

import com.example.ASMapi.dto.AppUserDto;
import com.example.ASMapi.request.RegisterRequest;

public interface AppUserService {
    public AppUserDto getCurrentRecord(String username);

    public AppUserDto createRecord(RegisterRequest registerRequest);
}
