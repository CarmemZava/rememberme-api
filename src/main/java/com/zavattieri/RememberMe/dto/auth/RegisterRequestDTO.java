package com.zavattieri.RememberMe.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDTO(
        //only attribute not included is role because by default every registered user is USER, not ADMIN
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String password
        //@NotNull UserRole role -> Keeping without Role for now, nothing defined yet Admin user
) {
}
