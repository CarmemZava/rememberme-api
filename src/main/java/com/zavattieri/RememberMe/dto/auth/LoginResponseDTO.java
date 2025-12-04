package com.zavattieri.RememberMe.dto.auth;

public record LoginResponseDTO( //DTO used to return data after authentication
                                String token,
                                String name,
                                String email,
                                String role
) {
}
