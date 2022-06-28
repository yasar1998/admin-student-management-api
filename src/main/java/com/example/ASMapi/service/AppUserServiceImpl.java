package com.example.ASMapi.service;

import com.example.ASMapi.dto.AppUserDto;
import com.example.ASMapi.entity.AppUser;
import com.example.ASMapi.exceptions.custom.UserNotFoundException;
import com.example.ASMapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService{

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public AppUserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AppUserDto getCurrentRecord(String username) {
        AppUser student = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));

        return modelMapper.map(student, AppUserDto.class);
    }
}
