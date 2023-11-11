package com.demo.userservice.service;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.userservice.persistence.model.AppUser;
import com.demo.userservice.persistence.model.RoleEnum;
import com.demo.userservice.persistence.repository.AppUserRepository;
import com.demo.userservice.security.UsernameAlreadyExistsException;
import com.demo.userservice.service.dto.RegistrationRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String register(RegistrationRequestDto credentials) {
        String username = credentials.username();
        if (appUserRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(String.format("Username '%s' already exists", username));
        }
        AppUser userToCreate = AppUser.builder().withUsername(username)
                .withPassword(passwordEncoder.encode(credentials.password()))
                .withRoles(Set.of(RoleEnum.USER))
                .withEnabled(true)
                .build();
        return appUserRepository.save(userToCreate)
                .getUsername();
    }

    @Transactional
    public String registerAdministrator(RegistrationRequestDto credentials) {
        return register(credentials, RoleEnum.ADMINISTRATOR);
    }

    @Transactional
    public String registerJournalist(RegistrationRequestDto credentials) {
        return register(credentials, RoleEnum.JOURNALIST);
    }

    @Transactional
    public String registerUser(RegistrationRequestDto credentials) {
        return register(credentials, RoleEnum.USER);
    }

    private String register(RegistrationRequestDto credentials, RoleEnum role) {
        String username = credentials.username();
        if (appUserRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(String.format("Username '%s' already exists", username));
        }
        AppUser userToCreate = AppUser.builder().withUsername(username)
                .withPassword(passwordEncoder.encode(credentials.password()))
                .withRoles(Set.of(role))
                .withEnabled(true)
                .build();
        return appUserRepository.save(userToCreate)
                .getUsername();
    }
}
