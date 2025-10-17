package org.yashgamerx.pokemonboot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PokemonDto {
    private String name;
    private Integer level;
    private String ability;
    private String regionName;
    private Integer regionId;
}
