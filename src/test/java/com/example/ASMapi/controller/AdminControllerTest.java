package com.example.ASMapi.controller;

import com.example.ASMapi.dto.AppUserDto;
import com.example.ASMapi.dto.IdResponse;
import com.example.ASMapi.entity.AppUser;
import com.example.ASMapi.repository.UserRepository;
import com.example.ASMapi.request.AssignRequest;
import com.example.ASMapi.security.utils.Roles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    protected final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        userRepository.save(new AppUser("yashar", passwordEncoder.encode("password"), "Yashar", "Mustafayev", Roles.ADMIN));
        userRepository.save(new AppUser("tural", passwordEncoder.encode("password"), "Tural", "Aliyev", Roles.STUDENT));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteStudentIfAuthenticatedAsAdmin() throws Exception {
        IdResponse idResponse = new IdResponse(2L);
        String output = objectMapper.writeValueAsString(idResponse);

        mockMvc.perform(delete("/admin/delete/student/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(output));

    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteStudentIfNotAuthenticatedAsAdmin() throws Exception {

        mockMvc.perform(delete("/admin/delete/student/2"))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", equalTo("UNAUTHORIZED")))
                .andExpect(jsonPath("$.message", equalTo("user is not authorized to access the resource")));

    }


    @Test
    void deleteStudentIfNotAuthenticated() throws Exception {

        mockMvc.perform(delete("/admin/delete/student/2"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", equalTo("UNAUTHENTICATED")))
                .andExpect(jsonPath("$.message", equalTo("authentication is required")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllStudents() throws Exception {
        List<AppUserDto> students = List.of(
                new AppUserDto("tural", "Aliyev", "Tural", Roles.STUDENT));

        String output = objectMapper.writeValueAsString(students);
        log.info(output);
        mockMvc.perform(get("/admin/students"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(output))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsers() throws Exception {
        List<AppUserDto> students = List.of(
                new AppUserDto("yashar", "Mustafayev", "Yashar", Roles.ADMIN),
                new AppUserDto("tural", "Aliyev", "Tural", Roles.STUDENT));

        String output = objectMapper.writeValueAsString(students);
        log.info(output);
        mockMvc.perform(get("/admin/users"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(output))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getStudent() throws Exception {
        AppUserDto studentDto = new AppUserDto("tural", "Aliyev", "Tural", Roles.STUDENT);
        mockMvc.perform(get("/admin/students/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", equalTo("tural")))
                .andExpect(jsonPath("$.lastName", equalTo("Aliyev")))
                .andExpect(jsonPath("$.firstName", equalTo("Tural")))
                .andExpect(jsonPath("$.roles", equalTo(Roles.STUDENT)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void assignRole() throws Exception {
        AssignRequest assignRequest = new AssignRequest("tural", "ADMIN");

        mockMvc.perform(post("/admin/giveRole").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(assignRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.username", equalTo("tural")))
                .andExpect(jsonPath("$.firstName", equalTo("Tural")))
                .andExpect(jsonPath("$.lastName", equalTo("Aliyev")))
                .andExpect(jsonPath("$.roles", equalTo(Roles.ADMIN)));
    }
}