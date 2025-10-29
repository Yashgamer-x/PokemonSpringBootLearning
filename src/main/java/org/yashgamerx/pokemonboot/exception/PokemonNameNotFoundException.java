package org.yashgamerx.pokemonboot.exception;

public class PokemonNameNotFoundException extends PokemonNotFoundException {
    public PokemonNameNotFoundException(String name) {
        super("Pokemon region with name '" + name + "' was not found.");
    }
}
