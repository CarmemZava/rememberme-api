package com.zavattieri.RememberMe.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO( //Record is immutable data carrier, used for transferring data (DTO)
                               //No need for getters, setters, constructors
                               //Login only needs email and password information
                               @NotBlank String email,
                               @NotBlank String password
) {
}
