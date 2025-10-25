package org.yashgamerx.pokemonboot.dto;

import lombok.Getter;
import lombok.Setter;
import org.yashgamerx.pokemonboot.dao.PokemonType;

import java.util.List;

/**
 * Data Transfer Object representing a Pokémon.
 * Used to encapsulate Pokémon data for transport between application layers.
 **/
@Getter @Setter
public class PokemonDto {
    private String name;
    private Integer level;
    private String ability;
    private List<PokemonType> pokemonTypes;
    private String regionName;
    private Integer regionId;
}
