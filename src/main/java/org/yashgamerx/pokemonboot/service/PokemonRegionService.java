package org.yashgamerx.pokemonboot.service;

import org.springframework.stereotype.Service;
import org.yashgamerx.pokemonboot.dao.PokemonRegion;
import org.yashgamerx.pokemonboot.dto.PokemonDto;
import org.yashgamerx.pokemonboot.dto.PokemonRegionDto;
import org.yashgamerx.pokemonboot.repo.PokemonRegionRepository;

import java.util.Optional;

@Service
public class PokemonRegionService {
    private final PokemonRegionRepository pokemonRegionRepository;

    public PokemonRegionService(PokemonRegionRepository pokemonRegionRepository) {
        this.pokemonRegionRepository = pokemonRegionRepository;
    }

    public Optional<PokemonRegion> getPokemonRegionByDto(PokemonDto pokemonDto) {
        if (pokemonDto.name() != null && !pokemonDto.name().isEmpty()) {
            return pokemonRegionRepository.findPokemonRegionByName(pokemonDto.name());
        } else if (pokemonDto.name() != null) {
            return pokemonRegionRepository.findPokemonRegionById(pokemonDto.regionId());
        }
        return Optional.empty();
    }

    public void savePokemonRegion(PokemonRegion pokemonRegion) {
        pokemonRegionRepository.save(pokemonRegion);
    }

    public PokemonRegion convertPokemonRegionDtoToPokemonRegion(PokemonRegionDto pokemonRegionDto) {
        return PokemonRegion.builder()
                .name(pokemonRegionDto.name())
                .population(pokemonRegionDto.population())
                .build();
    }
}
