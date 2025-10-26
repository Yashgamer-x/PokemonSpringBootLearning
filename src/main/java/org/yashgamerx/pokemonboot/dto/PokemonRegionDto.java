package org.yashgamerx.pokemonboot.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PokemonRegionDto(
        @NotNull(message = "Name is mandatory")
        @NotBlank(message = "Name is mandatory")
        String name,
        @NotNull(message = "Population is mandatory")
        @Min(value = 1, message = "Population shall be greater than 1")
        Integer population
) {}