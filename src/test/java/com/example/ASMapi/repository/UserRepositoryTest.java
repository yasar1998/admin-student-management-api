package com.example.ASMapi.repository;

import com.example.ASMapi.entity.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        AppUser student1ToSave = new AppUser("yasar1998",
                passwordEncoder.encode("password"),
                "Yashar",
                "Mustafayev",
                "ADMIN");
        AppUser student2ToSave = new AppUser("tom12",
                passwordEncoder.encode("password"),
                "Tom",
                "Smith",
                "USER");
        userRepository.save(student1ToSave);
        userRepository.save(student2ToSave);
    }

    @Test
    void testFindByUsername() {
        AppUser studentToCompare = new AppUser("yasar1998",
                "password",
                "Yashar",
                "Mustafayev",
                "ADMIN");

        assertEquals(studentToCompare.getUsername(), userRepository.findByUsername("yasar1998").get().getUsername());
        assertTrue(passwordEncoder.matches(studentToCompare.getPassword(), userRepository.findByUsername("yasar1998").get().getPassword()));
        assertEquals(studentToCompare.getFirstName(), userRepository.findByUsername("yasar1998").get().getFirstName());
        assertEquals(studentToCompare.getLastName(), userRepository.findByUsername("yasar1998").get().getLastName());
        assertEquals(studentToCompare.getRoles(), userRepository.findByUsername("yasar1998").get().getRoles());
    }

    @Test
    void testFindByUsernameIfNotExists(){
        assertThrows(NoSuchElementException.class,() -> userRepository.findByUsername("yasar").get().getUsername());
    }
}