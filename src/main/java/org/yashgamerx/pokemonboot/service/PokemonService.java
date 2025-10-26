package org.yashgamerx.pokemonboot.service;

import org.springframework.stereotype.Service;
import org.yashgamerx.pokemonboot.dao.Pokemon;
import org.yashgamerx.pokemonboot.dao.PokemonRegion;
import org.yashgamerx.pokemonboot.dto.PokemonDto;
import org.yashgamerx.pokemonboot.exception.PokemonException;
import org.yashgamerx.pokemonboot.repo.PokemonRepository;

import java.util.Optional;

@Service
public class PokemonService {
    private final PokemonRepository pokemonRepository;

    public PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public Pokemon createPokemon(PokemonDto pokemonDto, PokemonRegion pokemonRegion) {
        if (pokemonDto.name() == null || pokemonDto.name().isBlank()) {
            throw new PokemonException("Pokemon name must not be null or blank");
        }
        if (pokemonDto.ability() == null || pokemonDto.ability().isBlank()) {
            throw new PokemonException("Pokemon ability must not be null or blank");
        }
        if (pokemonDto.level() == null || pokemonDto.level() < 1) {
            throw new PokemonException("Pokemon level must be a positive integer");
        }
        var pokemon = new Pokemon();
        pokemon.setName(pokemonDto.name());
        pokemon.setAbility(pokemonDto.ability());
        pokemon.setLevel(pokemonDto.level());
        pokemon.setPokemonRegion(pokemonRegion);
        return pokemon;
    }

    public void savePokemon(Pokemon pokemon) {
        pokemonRepository.save(pokemon);
    }

    public Optional<Pokemon> findPokemonByName(String name) {
        return pokemonRepository.findPokemonByName(name);
    }

    public void updatePokemon(PokemonDto pokemonDto, Pokemon pokemon, Optional<PokemonRegion> pokemonRegion) {
        if(pokemonDto.ability() != null && !pokemonDto.ability().isEmpty()){
            pokemon.setAbility(pokemonDto.ability());
        }
        if(pokemonDto.level() != null && pokemonDto.level()>0){
            pokemon.setLevel(pokemonDto.level());
        }
        if(pokemonDto.pokemonTypes() != null && !pokemonDto.pokemonTypes().isEmpty()){
            pokemon.setTypes(pokemonDto.pokemonTypes());
        }
        pokemonRegion.ifPresent(pokemon::setPokemonRegion);
    }

    public void deletePokemonById(Integer id) {
        pokemonRepository.deleteById(id);
    }
}
