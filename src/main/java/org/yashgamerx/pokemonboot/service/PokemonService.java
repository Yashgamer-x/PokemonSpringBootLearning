package org.yashgamerx.pokemonboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yashgamerx.pokemonboot.dao.Pokemon;
import org.yashgamerx.pokemonboot.dao.PokemonRegion;
import org.yashgamerx.pokemonboot.dto.PokemonDto;
import org.yashgamerx.pokemonboot.repo.PokemonRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PokemonService {
    private final PokemonRepository pokemonRepository;

    public Pokemon createPokemon(PokemonDto pokemonDto, PokemonRegion pokemonRegion) {
        return Pokemon.builder()
                .name(pokemonDto.name())
                .ability(pokemonDto.ability())
                .level(pokemonDto.level())
                .types(pokemonDto.pokemonTypes())
                .pokemonRegion(pokemonRegion)
                .build();
    }

    @Transactional
    public void savePokemon(Pokemon pokemon) {
        pokemonRepository.save(pokemon);
    }

    @Transactional(readOnly = true)
    public Optional<Pokemon> findPokemonByName(String name) {
        return pokemonRepository.findPokemonByName(name);
    }

    @Transactional(readOnly = true)
    public Optional<Pokemon> findPokemonById(Integer id) {
        return pokemonRepository.findById(id);
    }

    public void updatePokemon(PokemonDto pokemonDto, Pokemon pokemon, Optional<PokemonRegion> pokemonRegion) {
        if(!pokemonDto.ability().equals(pokemon.getAbility())){
            pokemon.setAbility(pokemonDto.ability());
        }
        if(!pokemonDto.level().equals(pokemon.getLevel())){
            pokemon.setLevel(pokemonDto.level());
        }
        if(!pokemonDto.pokemonTypes().equals(pokemon.getTypes())){
            pokemon.setTypes(pokemonDto.pokemonTypes());
        }
        pokemonRegion.ifPresent(pokemon::setPokemonRegion);
    }

    public PokemonDto mapPokemonToDto(Pokemon pokemon) {
        return PokemonDto.builder()
                .name(pokemon.getName())
                .level(pokemon.getLevel())
                .ability(pokemon.getAbility())
                .pokemonTypes(pokemon.getTypes())
                .regionName(pokemon.getPokemonRegion().getName())
                .build();
    }

    @Transactional
    public void deletePokemonById(Integer id) {
        pokemonRepository.deleteById(id);
    }
}
