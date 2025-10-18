package org.yashgamerx.pokemonboot.exception;

public class PokemonRegionException extends RuntimeException{
    public PokemonRegionException(String name) {
        super("Pokemon Region Not Found: "+name);
    }
}
