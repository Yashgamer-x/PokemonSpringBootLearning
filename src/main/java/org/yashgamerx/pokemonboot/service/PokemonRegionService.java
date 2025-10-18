package org.yashgamerx.pokemonboot.service;

import org.springframework.stereotype.Service;
import org.yashgamerx.pokemonboot.dao.PokemonRegion;
import org.yashgamerx.pokemonboot.dto.PokemonDto;
import org.yashgamerx.pokemonboot.repo.PokemonRegionRepository;

import java.util.Optional;

@Service
public class PokemonRegionService {
    private final PokemonRegionRepository pokemonRegionRepository;

    public PokemonRegionService(PokemonRegionRepository pokemonRegionRepository) {
        this.pokemonRegionRepository = pokemonRegionRepository;
    }

    /**
     * Retrieves a {@link PokemonRegion} based on the data provided in the {@link PokemonDto}.
     * <p>
     * The method attempts to find the region using the name first. If the name is null or empty,
     * it falls back to using the region ID. If neither is available or no matching region is found,
     * it returns {@link Optional#empty()}.
     *
     * @param pokemonDto the DTO containing region name or region ID
     * @return an {@link Optional} containing the matching {@link PokemonRegion}, or {@link Optional#empty()} if not found
     */
    public Optional<PokemonRegion> getPokemonRegionByDto(PokemonDto pokemonDto) {
        if (pokemonDto.getRegionName() != null && !pokemonDto.getRegionName().isEmpty()) {
            return pokemonRegionRepository.findPokemonRegionByName(pokemonDto.getRegionName());
        } else if (pokemonDto.getRegionId() != null) {
            return pokemonRegionRepository.findPokemonRegionById(pokemonDto.getRegionId());
        }
        return Optional.empty();
    }

    public void savePokemonRegion(PokemonRegion pokemonRegion) {
        pokemonRegionRepository.save(pokemonRegion);
    }

    /**
     * Validates the given {@link PokemonRegion} to ensure it meets basic criteria.
     * <p>
     * This method checks:
     * <ul>
     *   <li>The region object itself is not {@code null}</li>
     *   <li>The region name is not {@code null}, or empty</li>
     *   <li>The population is a positive integer (≥ 1)</li>
     * </ul>
     * If any condition fails, an {@link IllegalArgumentException} is thrown.
     *
     * @param pokemonRegion the {@link PokemonRegion} to validate
     * @throws IllegalArgumentException if the region is invalid or missing required fields
     */
    public void validatePokemonRegion(PokemonRegion pokemonRegion) {
        if(
                pokemonRegion.getName() == null ||
                pokemonRegion.getName().isEmpty() ||
                pokemonRegion.getPopulation() < 1
        ) {
            throw new IllegalArgumentException("Region was unable to meet the criteria");
        }
    }
}
