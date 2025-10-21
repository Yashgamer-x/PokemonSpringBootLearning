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

    /**
     * Creates a new {@link Pokemon} entity from the provided {@link PokemonDto} and {@link PokemonRegion}.
     * <p>
     * This method performs strict validation to ensure that all required fields are present and valid:
     * <ul>
     *   <li>{@code pokemonDto} must not be {@code null}</li>
     *   <li>{@code pokemonRegion} must not be {@code null}</li>
     *   <li>{@code name} and {@code ability} must be non-null and non-blank</li>
     *   <li>{@code level} must be a positive integer (≥ 1)</li>
     * </ul>
     * If any of these conditions are violated, an {@link IllegalArgumentException} is thrown.
     * <p>
     * Once validated, a new {@link Pokemon} instance is initialized and populated with the DTO values
     * and associated region, ready for persistence.
     *
     * @param pokemonDto the data transfer object containing Pokémon attributes
     * @param pokemonRegion the resolved region to associate with the Pokémon
     * @return a fully initialized {@link Pokemon} entity
     * @throws IllegalArgumentException if any required field is missing or invalid
     */
    public Pokemon createPokemon(PokemonDto pokemonDto, PokemonRegion pokemonRegion) {
        if (pokemonDto.getName() == null || pokemonDto.getName().isBlank()) {
            throw new PokemonException("Pokemon name must not be null or blank");
        }
        if (pokemonDto.getAbility() == null || pokemonDto.getAbility().isBlank()) {
            throw new PokemonException("Pokemon ability must not be null or blank");
        }
        if (pokemonDto.getLevel() == null || pokemonDto.getLevel() < 1) {
            throw new PokemonException("Pokemon level must be a positive integer");
        }
        var pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setAbility(pokemonDto.getAbility());
        pokemon.setLevel(pokemonDto.getLevel());
        pokemon.setPokemonRegion(pokemonRegion);
        return pokemon;
    }

    /**
     * Persists the given {@link Pokemon} entity to the database.
     * <p>
     * Delegates the save operation to {@code pokemonRepository}, which handles
     * insertion or update based on the entity's state.
     *
     * @param pokemon the {@link Pokemon} entity to be saved
     * @throws IllegalArgumentException if {@code pokemon} is {@code null}
     */
    public void savePokemon(Pokemon pokemon) {
        pokemonRepository.save(pokemon);
    }

    /**
     * Retrieves a {@link Pokemon} entity by its name using data from the provided {@link PokemonDto}.
     * <p>
     * Extracts the Pokémon name from the DTO and delegates the lookup to {@code pokemonRepository}.
     * Returns an {@link Optional} containing the matching Pokémon if found, or an empty {@link Optional} otherwise.
     *
     * @param pokemonDto the data transfer object containing the Pokémon name
     * @return an {@link Optional} of {@link Pokemon} matching the given name
     * @throws IllegalArgumentException if {@code pokemonDto} is {@code null} or its name is {@code null}
     */
    public Optional<Pokemon> findPokemonByNameByDto(PokemonDto pokemonDto) {
        var pokemonName = pokemonDto.getName();
        return pokemonRepository.findPokemonByName(pokemonName);
    }

    public Optional<Pokemon> findPokemonByName(String name) {
        return pokemonRepository.findPokemonByName(name);
    }

    /**
     * Updates the attributes of an existing {@link Pokemon} entity using data from the provided {@link PokemonDto}.
     * <p>
     * This method performs conditional updates:
     * <ul>
     *   <li>If {@code ability} is non-null and non-empty, updates the Pokémon's ability.</li>
     *   <li>If {@code level} is non-null and greater than zero, updates the Pokémon's level.</li>
     *   <li>If a {@link PokemonRegion} is present, updates the Pokémon's region.</li>
     * </ul>
     * Fields that are {@code null}, empty, or invalid are ignored to preserve existing values.
     *
     * @param pokemonDto the DTO containing updated Pokémon data
     * @param pokemon the existing {@link Pokemon} entity to be modified
     * @param pokemonRegion an {@link Optional} containing the new region, if available
     */
    public void updatePokemon(PokemonDto pokemonDto, Pokemon pokemon, Optional<PokemonRegion> pokemonRegion) {
        if(pokemonDto.getAbility() != null && !pokemonDto.getAbility().isEmpty()){
            pokemon.setAbility(pokemonDto.getAbility());
        }
        if(pokemonDto.getLevel() != null && pokemonDto.getLevel()>0){
            pokemon.setLevel(pokemonDto.getLevel());
        }
        pokemonRegion.ifPresent(pokemon::setPokemonRegion);
    }

    public void deletePokemonById(Integer id) {
        pokemonRepository.deleteById(id);
    }
}
