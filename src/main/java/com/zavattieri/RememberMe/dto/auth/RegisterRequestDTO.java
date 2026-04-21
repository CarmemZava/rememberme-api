package com.zavattieri.RememberMe.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        //only attribute not included is role because by default every registered user is USER, not ADMIN
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6, message = "Password must have at least 6 characters") String password
        //@NotNull UserRole role -> Keeping without Role for now, nothing defined yet Admin user
        //@Size defines length of the password
        //I could use @Pattern to define other conditions such as especial characters,at least one number, etc
) {
}
