package org.yashgamerx.pokemonboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yashgamerx.pokemonboot.controller.PokemonController;

@SpringBootApplication
public class PokemonBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(PokemonBootApplication.class, args);
    }

}
