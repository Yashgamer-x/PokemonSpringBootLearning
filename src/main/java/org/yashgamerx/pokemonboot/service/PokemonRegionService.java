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
}
