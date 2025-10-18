package org.yashgamerx.pokemonboot.exception;

public class PokemonException extends RuntimeException {
    public PokemonException(String message) {
        super("Unable to find Pokemon: "+message);
    }
}
