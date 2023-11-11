package com.demo.userservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.userservice.service.RegistrationService;
import com.demo.userservice.service.dto.RegistrationRequestDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/register")
public class RegistrationRestController {

    private static final String USERNAME_KEY = "username";
    private final RegistrationService registrationService;

    @PostMapping("/administrator")
    public ResponseEntity<Map<String, String>> registerAdministrator(
            @RequestBody @Valid RegistrationRequestDto request) {
        String registeredUsername = registrationService.registerAdministrator(request);
        Map<String, String> response = new HashMap<>();
        response.put(USERNAME_KEY, registeredUsername);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/journalist")
    public ResponseEntity<Map<String, String>> registerJournalist(@RequestBody @Valid RegistrationRequestDto request) {
        String registeredUsername = registrationService.registerJournalist(request);
        Map<String, String> response = new HashMap<>();
        response.put(USERNAME_KEY, registeredUsername);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/user")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody @Valid RegistrationRequestDto request) {
        String registeredUsername = registrationService.registerUser(request);
        Map<String, String> response = new HashMap<>();
        response.put(USERNAME_KEY, registeredUsername);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
