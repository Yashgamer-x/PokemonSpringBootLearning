package org.yashgamerx.pokemonboot.exception;

public class PokemonIdNotFoundException extends RuntimeException {
    public PokemonIdNotFoundException(String id) {
        super("Pokemon region with id '" + id + "' was not found.");

    }
}
