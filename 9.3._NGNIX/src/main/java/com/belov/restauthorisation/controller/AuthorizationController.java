package com.belov.restauthorisation.controller;

import com.belov.restauthorisation.exception.InvalidCredentials;
import com.belov.restauthorisation.exception.UnauthorizedUser;
import com.belov.restauthorisation.model.Authorities;
import com.belov.restauthorisation.model.User;
import com.belov.restauthorisation.resolver.UserParam;
import com.belov.restauthorisation.service.AuthorizationService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
public class AuthorizationController {

    private AuthorizationService service;

    public AuthorizationController(AuthorizationService service) {
        this.service = service;
    }

    @GetMapping("/authorize")
    public List<Authorities> getUserAuthorities(@UserParam @Valid User user) {
        return service.getAuthorities(user);
    }

    @ExceptionHandler(InvalidCredentials.class)
    public ResponseEntity<String> InvalidCredentialsHandler(InvalidCredentials ice) {
        return new ResponseEntity<>(ice.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedUser.class)
    public ResponseEntity<String> InvalidCredentialsHandler(UnauthorizedUser uue) {
        System.out.printf(uue.getMessage());
        return new ResponseEntity<>(uue.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> invalidCredentialsHandler(ConstraintViolationException cve) {
        return new ResponseEntity<>("слишком короткое или длинное имя пользователя/пароль", HttpStatus.BAD_REQUEST);
    }
}