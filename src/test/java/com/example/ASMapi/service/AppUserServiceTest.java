package com.example.ASMapi.service;

import com.example.ASMapi.dto.AppUserDto;
import com.example.ASMapi.entity.AppUser;
import com.example.ASMapi.repository.UserRepository;
import com.example.ASMapi.request.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    void updatePassword() {
    }
}