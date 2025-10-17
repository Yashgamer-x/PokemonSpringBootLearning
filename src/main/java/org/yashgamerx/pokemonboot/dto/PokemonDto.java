package org.yashgamerx.pokemonboot.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object representing a Pokémon.
 * Used to encapsulate Pokémon data for transport between application layers.
 **/
@Getter @Setter
public class PokemonDto {
    private Integer id;
    private String name;
    private Integer level;
    private String ability;
    private String regionName;
    private Integer regionId;
}
