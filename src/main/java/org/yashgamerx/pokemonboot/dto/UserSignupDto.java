package org.yashgamerx.pokemonboot.dto;

public record UserSignupDto (
        String username,
        String email,
        String password,
        String confirmPassword
){}
