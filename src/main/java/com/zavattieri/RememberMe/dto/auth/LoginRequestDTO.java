package com.zavattieri.RememberMe.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO( //Record is immutable data carrier, used for transferring data (DTO)
                               //No need for getters, setters, constructors
                               //Login only needs email and password information
                               @NotBlank @Email String email,
                               @NotBlank @Size(min = 6, message = "Password must have at least 6 characters") String password
) {
}
