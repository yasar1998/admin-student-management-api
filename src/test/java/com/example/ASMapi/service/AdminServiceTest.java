package com.example.ASMapi.service;

import com.example.ASMapi.dto.AppUserDto;
import com.example.ASMapi.dto.IdResponse;
import com.example.ASMapi.entity.AppUser;
import com.example.ASMapi.exceptions.custom.UserNotFoundException;
import com.example.ASMapi.repository.UserRepository;
import com.example.ASMapi.request.AssignRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

class AdminServiceTest {

    private ModelMapper modelMapper;

    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;

    private AdminServiceImpl adminServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
        passwordEncoder = new BCryptPasswordEncoder();
        adminServiceImpl = new AdminServiceImpl(userRepository, passwordEncoder, modelMapper);
    }

    @Test
    void testGetAllRecords() {

        List<AppUser> userDtoList = List.of(
                new AppUser("yasar1998",
                        passwordEncoder.encode("password"),
                        "Yashar",
                        "Mustafayev",
                        "ADMIN"),
                new AppUser("john",
                        passwordEncoder.encode("password"),
                        "John",
                        "Simone",
                        "STUDENT"));
        Mockito.when(userRepository.findAll()).thenReturn(userDtoList);
        List<AppUserDto> actualList = adminServiceImpl.getAllRecords();

        List<AppUserDto> expectedList = userDtoList.stream()
                .map(user -> modelMapper.map(user, AppUserDto.class))
                .collect(Collectors.toList());

        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList.get(0).getFirstName(), actualList.get(0).getFirstName());
        assertEquals(expectedList.get(0).getLastName(), actualList.get(0).getLastName());
        assertEquals(expectedList.get(0).getRoles(), actualList.get(0).getRoles());
        assertEquals(expectedList.get(0).getUsername(), actualList.get(0).getUsername());
        assertEquals(expectedList.get(1).getFirstName(), actualList.get(1).getFirstName());
        assertEquals(expectedList.get(1).getLastName(), actualList.get(1).getLastName());
        assertEquals(expectedList.get(1).getRoles(), actualList.get(1).getRoles());
        assertEquals(expectedList.get(1).getUsername(), actualList.get(1).getUsername());

        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testGetAllStudentRecords() {

        List<AppUser> studentList = List.of(
                new AppUser("john",
                        passwordEncoder.encode("password"),
                        "John",
                        "Simone",
                        "STUDENT"));

        Mockito.when(userRepository.findAll()).thenReturn(studentList);
        List<AppUserDto> actualStudentDtoList = adminServiceImpl.getAllStudentRecords();

        List<AppUserDto>expectedStudentDtoList = studentList.stream().filter(appUser -> appUser.getRoleList().contains("STUDENT")).map(student-> modelMapper.map(student, AppUserDto.class)).collect(Collectors.toList());


        assertEquals(expectedStudentDtoList.size(), actualStudentDtoList.size());
        assertEquals(expectedStudentDtoList.get(0).getFirstName(), actualStudentDtoList.get(0).getFirstName());
        assertEquals(expectedStudentDtoList.get(0).getLastName(), actualStudentDtoList.get(0).getLastName());
        assertEquals(expectedStudentDtoList.get(0).getRoles(), actualStudentDtoList.get(0).getRoles());
        assertEquals(expectedStudentDtoList.get(0).getUsername(), actualStudentDtoList.get(0).getUsername());


        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }


    @Test
    void testGetStudentRecordByIdIfNotExists() {
        Mockito.when(userRepository.findById(anyLong())).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> adminServiceImpl.getStudentRecordById(anyLong()));

        Mockito.verify(userRepository, Mockito.times(1)).findById(anyLong());
    }

    @Test
    void testGetStudentRecordByIdIfExists() {
        AppUser appUser = new AppUser("yasar1998",
                passwordEncoder.encode("password"),
                "Yashar",
                "Mustafayev",
                "STUDENT");
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(appUser));
        AppUserDto appUserDto = adminServiceImpl.getStudentRecordById(1L);

        AppUserDto expectedUserDto = modelMapper.map(appUser, AppUserDto.class);

        assertEquals(expectedUserDto.getFirstName(), appUserDto.getFirstName());
        assertEquals(expectedUserDto.getLastName(), appUserDto.getLastName());
        assertEquals(expectedUserDto.getUsername(), appUserDto.getUsername());
        assertEquals(expectedUserDto.getRoles(), appUserDto.getRoles());

        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testAddRole() {
        AssignRequest assignRequest = new AssignRequest("tom", "ADMIN");
        AppUser appUser = new AppUser("tom", "password", "Tom", "Smith", "ADMIN");

        Mockito.when(userRepository.findByUsername(assignRequest.getUsername())).thenReturn(Optional.of(appUser));
        AppUserDto actualAppUserDto = adminServiceImpl.addRole(assignRequest);

        AppUserDto expectedAppUserDto = modelMapper.map(appUser, AppUserDto.class);

        assertEquals(expectedAppUserDto.getUsername(), actualAppUserDto.getUsername());
        assertEquals(expectedAppUserDto.getFirstName(), actualAppUserDto.getFirstName());
        assertEquals(expectedAppUserDto.getRoles(), actualAppUserDto.getRoles());
        assertEquals(expectedAppUserDto.getLastName(), actualAppUserDto.getLastName());

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(assignRequest.getUsername());
    }

    @Test
    void testDeleteStudentRecord(){
        AppUser appUser = new AppUser(1L,
                "yasar1998",
                passwordEncoder.encode("password"),
                "Yashar",
                "Mustafayev",
                "STUDENT");

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(appUser));
        IdResponse actualIdResponse = adminServiceImpl.deleteStudentRecord(1L);

        IdResponse expectedIdResponse = new IdResponse(1L);

        assertEquals(expectedIdResponse.getId(), actualIdResponse.getId());
        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }
}