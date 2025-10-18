package org.yashgamerx.pokemonboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yashgamerx.pokemonboot.dao.PokemonRegion;
import org.yashgamerx.pokemonboot.service.PokemonRegionService;

@Controller
@RequestMapping("/region")
public class PokemonRegionController {
    private final PokemonRegionService pokemonRegionService;

    public PokemonRegionController(PokemonRegionService pokemonRegionService) {
        this.pokemonRegionService = pokemonRegionService;
    }

    /**
     * Handles HTTP POST requests to add a new {@link PokemonRegion}.
     * <p>
     * This method performs the following steps:
     * <ul>
     *   <li>Validates the incoming {@link PokemonRegion} using {@code pokemonRegionService.validatePokemonRegion}</li>
     *   <li>Persists the region to the database via {@code pokemonRegionService.savePokemonRegion}</li>
     *   <li>Returns a success message containing the region name</li>
     * </ul>
     * If the region fails validation, an {@link IllegalArgumentException} is thrown.
     *
     * @param pokemonRegion the {@link PokemonRegion} object received from the request body
     * @return a success message indicating the region was added
     * @throws IllegalArgumentException if the region is invalid or missing required fields
     */
    @PostMapping("/add")
    public @ResponseBody String add(@RequestBody PokemonRegion pokemonRegion) {
        pokemonRegionService.validatePokemonRegion(pokemonRegion);
        pokemonRegionService.savePokemonRegion(pokemonRegion);
        return pokemonRegion.getName()+" is added successfully";
    }
}
