package com.example.ASMapi.exceptions;

import com.example.ASMapi.exceptions.custom.UnsuccessfulPasswordUpdateException;
import com.example.ASMapi.exceptions.custom.UserNotFoundException;
import com.example.ASMapi.exceptions.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<ErrorResponse> handleAuthenticationException(HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now().toString(), 404,"Bad Credentials", request.getServletPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request){
        BindingResult result = exception.getBindingResult();
        Map<String, String> validationErrors = new HashMap<>();
        result.getFieldErrors()
                .forEach(validationError -> validationErrors.put(validationError.getField().toString(), validationError.getDefaultMessage().toString()));

        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now().toString(),400, "Invalid request", request.getServletPath());
        errorResponse.setValidationErrors(validationErrors);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {UnsuccessfulPasswordUpdateException.class})
    public ResponseEntity<ErrorResponse> handleUnsuccessfulPasswordUpdateException(HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now().toString(),409, "Password cannot be changed", request.getServletPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(HttpServletRequest request, UserNotFoundException exception){
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now().toString(),404, exception.getMessage(), request.getServletPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
