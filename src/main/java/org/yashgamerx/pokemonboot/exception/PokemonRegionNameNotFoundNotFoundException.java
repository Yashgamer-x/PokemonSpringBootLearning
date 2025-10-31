package org.yashgamerx.pokemonboot.exception;

public class PokemonRegionNameNotFoundNotFoundException extends PokemonRegionNotFoundException {
    public PokemonRegionNameNotFoundNotFoundException(String name) {
        super("Pokemon region with name '" + name + "' was not found.");
    }
}
