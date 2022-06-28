package com.example.ASMapi.service;

import com.example.ASMapi.dto.IdResponse;
import com.example.ASMapi.entity.AppUser;
import com.example.ASMapi.exceptions.custom.UserNotFoundException;
import com.example.ASMapi.repository.UserRepository;
import com.example.ASMapi.security.utils.Roles;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{

    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public IdResponse deleteRecord(Long id) {
        AppUser appUser = userRepository.findById(id).filter(user -> user.getRoleList().contains(Roles.STUDENT)).orElseThrow(()->new UserNotFoundException("Student not found"));
        userRepository.delete(appUser);
        IdResponse idResponse = new IdResponse();
        idResponse.setId(id);
        return idResponse;
    }
}
