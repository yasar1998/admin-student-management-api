package com.example.ASMapi.service;

import com.example.ASMapi.entity.AppUser;
import com.example.ASMapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordEncoder = new BCryptPasswordEncoder();
        loginService = new LoginService(userRepository);
    }

    @Test
    void testLoadUserByUsername() {
        AppUser appUser = new AppUser("yasar1998",
                passwordEncoder.encode("password"),
                "Yashar",
                "Mustafayev",
                "ADMIN");

        Mockito.when(userRepository.findByUsername(appUser.getUsername())).thenReturn(Optional.of(appUser));
        UserDetails actualUser = loginService.loadUserByUsername(appUser.getUsername());

        UserDetails expectedUser = User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .authorities(new ArrayList<>())
                .build();

        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        assertEquals(expectedUser.getPassword(), expectedUser.getPassword());
        assertEquals(expectedUser.getAuthorities(), expectedUser.getAuthorities());

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(appUser.getUsername());
    }
}