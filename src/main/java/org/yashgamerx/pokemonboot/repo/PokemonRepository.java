package org.yashgamerx.pokemonboot.repo;

import org.springframework.data.repository.CrudRepository;
import org.yashgamerx.pokemonboot.dao.Pokemon;

import java.util.List;
import java.util.Optional;

public interface PokemonRepository extends CrudRepository<Pokemon, Integer> {
    List<Pokemon> findPokemonByAbilityEqualsIgnoreCase(String ability);
    Optional<Pokemon> findPokemonByName(String name);
}
