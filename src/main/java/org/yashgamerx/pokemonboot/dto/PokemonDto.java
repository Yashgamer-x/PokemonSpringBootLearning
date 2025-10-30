package org.yashgamerx.pokemonboot.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import org.yashgamerx.pokemonboot.dao.PokemonType;
import java.util.List;


@Builder
public record PokemonDto(
        @NotBlank
        String name,
        @NotNull @Min(value = 5)
        Integer level,
        @NotBlank
        String ability,
        @NotEmpty
        List<PokemonType> pokemonTypes,
        @NotBlank
        String regionName
) { }
