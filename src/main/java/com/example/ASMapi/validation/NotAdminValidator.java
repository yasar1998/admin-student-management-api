package com.example.ASMapi.validation;

import com.example.ASMapi.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class NotAdminValidator implements ConstraintValidator<NotAdmin, String> {

    private final UserRepository userRepository;

    public NotAdminValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(NotAdmin constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s == null || !s.contains("ADMIN");
    }
}
