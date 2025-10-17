package org.yashgamerx.pokemonboot.repo;

import org.springframework.data.repository.CrudRepository;
import org.yashgamerx.pokemonboot.dao.Pokemon;

import java.util.List;

public interface PokemonRepository extends CrudRepository<Pokemon, Integer> {
    List<Pokemon> findPokemonByAbilityEqualsIgnoreCase(String ability);
    Pokemon findPokemonByName(String name);
}
