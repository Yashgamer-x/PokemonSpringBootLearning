package org.yashgamerx.pokemonboot.exception;

public class PokemonRegionNotFoundException extends RuntimeException {
    public PokemonRegionNotFoundException(String name) {
        super("Pokemon region with name '" + name + "' was not found.");
    }
}
