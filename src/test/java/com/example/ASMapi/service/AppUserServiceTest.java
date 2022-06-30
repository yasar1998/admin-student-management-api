package com.example.ASMapi.service;

import com.example.ASMapi.dto.AppUserDto;
import com.example.ASMapi.entity.AppUser;
import com.example.ASMapi.repository.UserRepository;
import com.example.ASMapi.request.PasswordUpdateRequest;
import com.example.ASMapi.request.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class AppUserServiceTest {

    private ModelMapper modelMapper;

    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;

    private AppUserServiceImpl appUserServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
        passwordEncoder = new BCryptPasswordEncoder();
        appUserServiceImpl = new AppUserServiceImpl(userRepository, modelMapper, passwordEncoder);
    }
    @Test
    void getCurrentRecord() {
        AppUser appUser = new AppUser("yasar1998",
                passwordEncoder.encode("password"),
                "Yashar",
                "Mustafayev",
                "ADMIN");
        Mockito.when(userRepository.findByUsername(appUser.getUsername())).thenReturn(Optional.of(appUser));
        AppUserDto actualAppUserDto = appUserServiceImpl.getCurrentRecord(appUser.getUsername());

        AppUserDto expectedAppUserDto = modelMapper.map(appUser, AppUserDto.class);

        assertEquals(expectedAppUserDto.getFirstName(), actualAppUserDto.getFirstName());
        assertEquals(expectedAppUserDto.getLastName(), actualAppUserDto.getLastName());
        assertEquals(expectedAppUserDto.getUsername(), actualAppUserDto.getUsername());
        assertEquals(expectedAppUserDto.getRoles(), actualAppUserDto.getRoles());

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(appUser.getUsername());
    }

    @Test
    public void testCreateRecord(){
        AppUser appUser = new AppUser("yasar1998",
                passwordEncoder.encode("password"),
                "Yashar",
                "Mustafayev",
                "ADMIN");
        RegisterRequest registerRequest = modelMapper.map(appUser, RegisterRequest.class);

        Mockito.when(userRepository.save(appUser)).thenReturn(appUser);
        AppUserDto actualDto = appUserServiceImpl.createRecord(registerRequest);

        AppUserDto expectedDto = modelMapper.map(appUser, AppUserDto.class);

        assertEquals(expectedDto.getFirstName(), actualDto.getFirstName());
        assertEquals(expectedDto.getLastName(), actualDto.getLastName());
        assertEquals(expectedDto.getUsername(), actualDto.getUsername());
        assertEquals(expectedDto.getRoles(), actualDto.getRoles());

        Mockito.verify(userRepository, Mockito.times(1)).save(any());
    }

    @Test
    void testUpdatePassword() {
        AppUser appUser = new AppUser("yasar1998",
                passwordEncoder.encode("password"),
                "Yashar",
                "Mustafayev",
                "ADMIN");
        PasswordUpdateRequest passwordUpdateRequest =
                new PasswordUpdateRequest("password", "password123");
        Mockito.when(userRepository.findByUsername(appUser.getUsername())).thenReturn(Optional.of(appUser));
        Mockito.when(userRepository.save(appUser)).thenReturn(appUser);
        AppUserDto actualAppUserDto = appUserServiceImpl.updatePassword(passwordUpdateRequest, appUser.getUsername());

        AppUserDto expectedAppUserDto = modelMapper.map(appUser, AppUserDto.class);

        assertEquals(expectedAppUserDto.getUsername(), actualAppUserDto.getUsername());
        assertEquals(expectedAppUserDto.getFirstName(), actualAppUserDto.getFirstName());
        assertEquals(expectedAppUserDto.getLastName(), actualAppUserDto.getLastName());
        assertEquals(expectedAppUserDto.getRoles(), actualAppUserDto.getRoles());

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(appUser.getUsername());
        Mockito.verify(userRepository, Mockito.times(1)).save(appUser);
    }
}