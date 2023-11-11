package com.demo.userservice.service.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequestDto(
        @NotBlank
        @Length(max = 50, message = "Username should be no longer than {max} characters")
        String username,
        @NotBlank
        @Length(max = 72, message = "Password's length should be no longer than {max} characters")
        String password)
        implements Serializable {
}
