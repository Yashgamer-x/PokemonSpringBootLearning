package org.yashgamerx.pokemonboot.repo;

import org.springframework.data.repository.CrudRepository;
import org.yashgamerx.pokemonboot.dao.PokemonRegion;

import java.util.Optional;

public interface PokemonRegionRepository extends CrudRepository<PokemonRegion, Integer> {
    PokemonRegion findPokemonRegionById(Integer id);
    PokemonRegion findPokemonRegionByName(String name);
}
