package org.yashgamerx.pokemonboot.dto;

import org.yashgamerx.pokemonboot.dao.PokemonType;
import java.util.List;


public record PokemonDto(
        String name,
        Integer level,
        String ability,
        List<PokemonType> pokemonTypes,
        String regionName,
        Integer regionId
) { }
