package org.yashgamerx.pokemonboot.repo;

import org.springframework.data.repository.CrudRepository;
import org.yashgamerx.pokemonboot.dao.PokemonRegion;

import java.util.Optional;

public interface PokemonRegionRepository extends CrudRepository<PokemonRegion, Integer> {
    Optional<PokemonRegion> findPokemonRegionById(Integer id);
    Optional<PokemonRegion> findPokemonRegionByName(String name);
}
