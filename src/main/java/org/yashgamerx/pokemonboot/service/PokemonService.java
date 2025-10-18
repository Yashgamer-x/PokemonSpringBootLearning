package org.yashgamerx.pokemonboot.service;

import org.springframework.stereotype.Service;
import org.yashgamerx.pokemonboot.dao.Pokemon;
import org.yashgamerx.pokemonboot.dao.PokemonRegion;
import org.yashgamerx.pokemonboot.dto.PokemonDto;
import org.yashgamerx.pokemonboot.repo.PokemonRepository;

import java.util.Optional;

@Service
public class PokemonService {
    private final PokemonRepository pokemonRepository;

    public PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public Pokemon createPokemon(PokemonDto pokemonDto, PokemonRegion pokemonRegion) {
        var pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setAbility(pokemonDto.getAbility());
        pokemon.setLevel(pokemonDto.getLevel());
        pokemon.setPokemonRegion(pokemonRegion);
        return pokemon;
    }

    public void savePokemon(Pokemon pokemon) {
        pokemonRepository.save(pokemon);
    }

    public Optional<Pokemon> findPokemonByNameByDto(PokemonDto pokemonDto) {
        var pokemonName = pokemonDto.getName();
        return pokemonRepository.findPokemonByName(pokemonName);
    }

    public void updatePokemon(PokemonDto pokemonDto, Pokemon pokemon, Optional<PokemonRegion> pokemonRegion) {
        if(pokemonDto.getAbility() != null && !pokemonDto.getAbility().isEmpty()){
            pokemon.setAbility(pokemonDto.getAbility());
        }
        if(pokemonDto.getLevel() != null && pokemonDto.getLevel()>0){
            pokemon.setLevel(pokemonDto.getLevel());
        }
        pokemonRegion.ifPresent(pokemon::setPokemonRegion);
    }

}
