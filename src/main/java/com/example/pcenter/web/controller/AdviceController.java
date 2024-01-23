package com.example.pcenter.web.controller;

import com.example.pcenter.domain.exception.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BodyException handleResourceMapping(final ResourceNotFoundException e){
        return new BodyException(e.getMessage());
    }

    @ExceptionHandler(ResourceMappingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BodyException handleResourceMapping(final ResourceMappingException e){
        return new BodyException(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BodyException handleIllegalState(final IllegalStateException e){
        return new BodyException(e.getMessage());
    }

    @ExceptionHandler({AccessDeniedException.class,
            org.springframework.security.access.AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public BodyException handleAccessDenied(){
        return new BodyException("Access denied");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BodyException handleMethodArgumentNotValid(final MethodArgumentNotValidException e){
        BodyException bodyException = new BodyException("Valid failed");
        List<FieldError> errorList = e.getBindingResult().getFieldErrors();
        bodyException.setErrors(errorList.stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        return bodyException;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BodyException handleConstraintViolation(final ConstraintViolationException e){
        BodyException bodyException = new BodyException("Validation failed");
        bodyException.setErrors(e.getConstraintViolations().stream()
                .collect(Collectors.toMap(violation -> violation.getPropertyPath().toString()
                        ,violation -> violation.getMessage())));
        return bodyException;
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BodyException handleAuthentication(final AuthenticationException e){
        return new BodyException("Authentication failed");
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BodyException handleException(Exception e){
        e.printStackTrace();
        return new BodyException("Internal error");
    }
}

