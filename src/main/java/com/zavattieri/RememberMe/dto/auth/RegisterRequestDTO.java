package com.zavattieri.RememberMe.dto.auth;

import com.zavattieri.RememberMe.domain.user.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequestDTO(
        //only attribute not included is role because by default every registered user is USER, not ADMIN
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String password,
        @NotNull UserRole role
) {
}
