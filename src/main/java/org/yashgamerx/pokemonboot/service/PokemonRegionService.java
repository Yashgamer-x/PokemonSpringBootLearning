package org.yashgamerx.pokemonboot.service;

import org.springframework.stereotype.Service;
import org.yashgamerx.pokemonboot.dao.PokemonRegion;
import org.yashgamerx.pokemonboot.dto.PokemonDto;
import org.yashgamerx.pokemonboot.exception.PokemonRegionException;
import org.yashgamerx.pokemonboot.repo.PokemonRegionRepository;

import java.util.Optional;

@Service
public class PokemonRegionService {
    private final PokemonRegionRepository pokemonRegionRepository;

    public PokemonRegionService(PokemonRegionRepository pokemonRegionRepository) {
        this.pokemonRegionRepository = pokemonRegionRepository;
    }

    public Optional<PokemonRegion> getPokemonRegionByDto(PokemonDto pokemonDto){
        if(pokemonDto.getRegionName() != null && !pokemonDto.getRegionName().isEmpty()){
            return pokemonRegionRepository.findPokemonRegionByName(pokemonDto.getRegionName());
        } else if( pokemonDto.getRegionId() != null) {
            return pokemonRegionRepository.findPokemonRegionById(pokemonDto.getRegionId());
        }
        throw new PokemonRegionException(null);
    }
}
