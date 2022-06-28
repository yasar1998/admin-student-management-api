package com.example.ASMapi;

import com.example.ASMapi.entity.AppUser;
import com.example.ASMapi.repository.UserRepository;
import com.example.ASMapi.security.utils.Roles;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AsmApiApplication implements CommandLineRunner {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public AsmApiApplication(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(AsmApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userRepository.save(new AppUser("yashar", passwordEncoder.encode("password"), "Yashar", "Mustafayev", Roles.ADMIN));
		userRepository.save(new AppUser("tom", passwordEncoder.encode("password"), "Tom", "Smith", Roles.STUDENT));
		userRepository.save(new AppUser("tural", passwordEncoder.encode("password"), "Tural", "Aliyev", Roles.STUDENT));
	}



}
