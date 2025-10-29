package org.yashgamerx.pokemonboot.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.yashgamerx.pokemonboot.dao.PokemonType;
import java.util.List;


public record PokemonDto(
        @NotNull @NotBlank
        String name,
        @NotNull @Min(value = 5)
        Integer level,
        @NotNull @NotBlank
        String ability,
        @NotNull.List(value = {}) @NotEmpty.List(value = {})
        List<PokemonType> pokemonTypes,
        @NotNull @NotBlank
        String regionName
) { }
