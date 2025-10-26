package org.yashgamerx.pokemonboot.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yashgamerx.pokemonboot.dto.PokemonRegionDto;
import org.yashgamerx.pokemonboot.service.PokemonRegionService;

@Controller
@RequestMapping("/region")
public class PokemonRegionController {
    private final PokemonRegionService pokemonRegionService;

    public PokemonRegionController(PokemonRegionService pokemonRegionService) {
        this.pokemonRegionService = pokemonRegionService;
    }

    @PostMapping("/add")
    public @ResponseBody String add(@Valid @RequestBody PokemonRegionDto pokemonRegionDto) {
        var pokemonRegion = pokemonRegionService.convertPokemonRegionDtoToPokemonRegion(pokemonRegionDto);
        pokemonRegionService.savePokemonRegion(pokemonRegion);
        return pokemonRegion.getName()+" is added successfully";
    }
}
