package org.yashgamerx.pokemonboot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Pokemon API",
                description = "This document contains APIs related to Pokemon",
                contact = @Contact(
                        name = "Yash Desai",
                        email = "yashdesai121212@gmail.com"
                ),
                version = "1.0.0"
        )
)
@SpringBootApplication
public class PokemonBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(PokemonBootApplication.class, args);
    }

}
