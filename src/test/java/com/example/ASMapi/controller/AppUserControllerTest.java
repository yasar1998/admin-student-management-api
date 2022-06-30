package com.example.ASMapi.controller;

import com.example.ASMapi.entity.AppUser;
import com.example.ASMapi.repository.UserRepository;
import com.example.ASMapi.request.PasswordUpdateRequest;
import com.example.ASMapi.security.utils.Roles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AppUserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AppUserController appUserController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    protected ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        userRepository.save(new AppUser("yashar", passwordEncoder.encode("password"), "Yashar", "Mustafayev", Roles.STUDENT));
        userRepository.save(new AppUser("tural", passwordEncoder.encode("password"), "Tural", "Aliyev", Roles.STUDENT));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "yashar", roles = "STUDENT")
    void getCurrentUser() throws Exception {
        mockMvc.perform(get("/api/account"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.username", equalTo("yashar")))
                .andExpect(jsonPath("$.firstName", equalTo("Yashar")))
                .andExpect(jsonPath("$.lastName", equalTo("Mustafayev")))
                .andExpect(jsonPath("$.roles", equalTo("STUDENT")));
    }

    @Test
    @WithMockUser(username = "yashar", roles = "STUDENT")
    void updatePassword() throws Exception {
        PasswordUpdateRequest passwordUpdateRequest = new PasswordUpdateRequest("password", "password123");

        mockMvc.perform(put("/api/account/password/update").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(passwordUpdateRequest)))
                .andExpect(status().isOk());
    }
}