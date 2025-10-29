package org.yashgamerx.pokemonboot.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PokemonRegionDto(
        @NotNull @NotBlank
        String name,
        @NotNull @Min(value = 10)
        Integer population
) {}