package com.example.ASMapi.service;

import com.example.ASMapi.dto.AppUserDto;
import com.example.ASMapi.dto.IdResponse;
import com.example.ASMapi.entity.AppUser;
import com.example.ASMapi.exceptions.custom.UserNotFoundException;
import com.example.ASMapi.repository.UserRepository;
import com.example.ASMapi.request.AssignRequest;
import com.example.ASMapi.security.utils.Roles;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService{

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public AdminServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public IdResponse deleteStudentRecord(Long id) {
        AppUser appUser = userRepository.findById(id).filter(user -> user.getRoleList().contains(Roles.STUDENT)).orElseThrow(()->new UserNotFoundException("Student not found"));
        userRepository.delete(appUser);
        IdResponse idResponse = new IdResponse(id);
        return idResponse;
    }

    @Override
    public List<AppUserDto> getAllStudentRecords() {
        return userRepository.findAll().stream()
                .filter(student -> student.getRoleList().contains(Roles.STUDENT))
                .map(student -> modelMapper.map(student, AppUserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AppUserDto> getAllRecords() {
        return userRepository.findAll().stream()
                .map(student -> modelMapper.map(student, AppUserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AppUserDto getStudentRecordById(Long id) {
        AppUser appUser = userRepository.findById(id).filter(user -> user.getRoleList().contains(Roles.STUDENT)).orElseThrow(()->new UserNotFoundException("Student not found"));
        return modelMapper.map(appUser, AppUserDto.class);
    }

    @Override
    public AppUserDto addRole(AssignRequest assignRequest) {
        AppUser appUser = userRepository.findByUsername(assignRequest.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));
        appUser.setRoles(assignRequest.getRoles());
        userRepository.save(appUser);

        return modelMapper.map(appUser, AppUserDto.class);
    }
}
