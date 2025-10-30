package org.yashgamerx.pokemonboot.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PokemonRegionDto(
        @NotBlank
        String name,
        @NotNull @Min(value = 10)
        Integer population
) {}