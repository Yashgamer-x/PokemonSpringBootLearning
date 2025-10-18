package org.yashgamerx.pokemonboot.service;

import org.springframework.stereotype.Service;
import org.yashgamerx.pokemonboot.dao.Pokemon;
import org.yashgamerx.pokemonboot.dao.PokemonRegion;
import org.yashgamerx.pokemonboot.dto.PokemonDto;
import org.yashgamerx.pokemonboot.exception.PokemonException;
import org.yashgamerx.pokemonboot.exception.PokemonRegionException;
import org.yashgamerx.pokemonboot.repo.PokemonRepository;

import java.util.Optional;

@Service
public class PokemonService {
    private final PokemonRepository pokemonRepository;

    public PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public Pokemon createPokemon(PokemonDto pokemonDto, Optional<PokemonRegion> pokemonRegion) {
        var pokeRegion = pokemonRegion.orElseThrow(()->
                new PokemonRegionException(pokemonDto.getRegionName())
        );
        var pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setAbility(pokemonDto.getAbility());
        pokemon.setLevel(pokemonDto.getLevel());
        pokemon.setPokemonRegion(pokeRegion);
        return pokemon;
    }

    public void savePokemon(Pokemon pokemon) {
        pokemonRepository.save(pokemon);
    }

    public Pokemon findPokemonByNameByDto(PokemonDto pokemonDto) {
        var pokemonName = pokemonDto.getName();
        var pokemon = pokemonRepository.findPokemonByName(pokemonName);
        return pokemon.orElseThrow(()->new PokemonException(pokemonName));
    }

}
