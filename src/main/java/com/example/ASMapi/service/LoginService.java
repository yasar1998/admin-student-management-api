package com.example.ASMapi.service;

import com.example.ASMapi.entity.AppUser;
import com.example.ASMapi.exceptions.custom.UserNotFoundException;
import com.example.ASMapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser student = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));

        log.info("user found in database");
        return org.springframework.security.core.userdetails.User.builder()
                .username(student.getUsername())
                .password(student.getPassword())
                .authorities(new ArrayList<>())
                .build();
    }
}
