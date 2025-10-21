package org.yashgamerx.pokemonboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yashgamerx.pokemonboot.dao.Pokemon;
import org.yashgamerx.pokemonboot.dao.PokemonRegion;
import org.yashgamerx.pokemonboot.dto.PokemonDto;
import org.yashgamerx.pokemonboot.exception.PokemonException;
import org.yashgamerx.pokemonboot.exception.PokemonRegionException;
import org.yashgamerx.pokemonboot.service.PokemonRegionService;
import org.yashgamerx.pokemonboot.service.PokemonService;

@Controller
@RequestMapping("/pokemon")
public class PokemonController {
    Logger log = LoggerFactory.getLogger(PokemonController.class);
    private final PokemonRegionService pokemonRegionService;
    private final PokemonService pokemonService;

    public PokemonController(
            PokemonRegionService pokemonRegionService,
            PokemonService pokemonService
    ) {
        this.pokemonRegionService = pokemonRegionService;
        this.pokemonService = pokemonService;
    }

    @GetMapping("/search")
    public ResponseEntity<Pokemon> pokemon(@RequestParam String name) {
        var pokemonOptional = pokemonService.findPokemonByName(name);
        Pokemon pokemon;
        if (pokemonOptional.isPresent()) {
            pokemon = pokemonOptional.get();
        }else{
            log.error("Pokemon not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Pokemon found with name {}",pokemon.getName());
        return ResponseEntity
                .ok()
                .body(pokemon);
    }

    /**
     * Handles HTTP POST requests to add a new Pokémon.
     * <p>
     * This method performs the following steps:
     * <ol>
     *   <li>Extracts region information from the provided {@link PokemonDto}.</li>
     *   <li>Attempts to retrieve the corresponding {@link PokemonRegion} using the region name or ID.</li>
     *   <li>If the region is not found, throws a {@link PokemonRegionException}.</li>
     *   <li>Creates a new {@link Pokemon} instance using the DTO and resolved region.</li>
     *   <li>Saves the Pokémon to the database via {@code pokemonService}.</li>
     *   <li>Returns a success message indicating the Pokémon and its region.</li>
     * </ol>
     *
     * @param pokemonDto the data transfer object containing Pokémon details
     * @return a success message indicating the Pokémon was added and its region
     * @throws PokemonRegionException if the region name or ID is invalid or not found
     */
    @PostMapping("/add")
    public @ResponseBody String addPokemon(@RequestBody PokemonDto pokemonDto) {
        var pokemonRegionOptional = pokemonRegionService.getPokemonRegionByDto(pokemonDto);
        var pokemonRegion = pokemonRegionOptional.orElseThrow(()->
                new PokemonRegionException("Unable to find Pokemon Region")
        );
        var pokemon = pokemonService.createPokemon(pokemonDto, pokemonRegion);
        pokemonService.savePokemon(pokemon);
        log.info("Pokemon Added Successfully");
        return pokemon.getName()+" was added successfully to "+pokemonRegion.getName()+" Region";
    }

    /**
     * Handles HTTP PUT requests to update an existing Pokémon.
     * <p>
     * This method performs the following steps:
     * <ol>
     *   <li>Attempts to find an existing {@link Pokemon} using the name provided in {@link PokemonDto}.</li>
     *   <li>If the Pokémon is not found, throws a {@link PokemonException}.</li>
     *   <li>Resolves the target {@link PokemonRegion} using the region name or ID from the DTO.</li>
     *   <li>Updates the Pokémon's attributes using the provided DTO and resolved region.</li>
     *   <li>Persists the updated Pokémon to the database via {@code pokemonService}.</li>
     *   <li>Returns a success message indicating the Pokémon was updated.</li>
     * </ol>
     *
     * @param pokemonDto the data transfer object containing updated Pokémon details
     * @return a success message confirming the Pokémon update
     * @throws PokemonException if the Pokémon with the given name is not found
     */
    @PutMapping("/update")
    public @ResponseBody String updatePokemon(@RequestBody PokemonDto pokemonDto){
        var pokemonOptional = pokemonService.findPokemonByNameByDto(pokemonDto);
        var pokemon = pokemonOptional.orElseThrow(()->new PokemonException("Unable to find Pokemon Region"));
        var pokeRegion = pokemonRegionService.getPokemonRegionByDto(pokemonDto);
        pokemonService.updatePokemon(pokemonDto, pokemon, pokeRegion);
        pokemonService.savePokemon(pokemon);
        return pokemon.getName()+" was updated successfully";
    }

    @DeleteMapping("/delete")
    public @ResponseBody String deletePokemon(@RequestParam String name){
        var pokemonOptional = pokemonService.findPokemonByName(name);
        var pokemon = pokemonOptional.orElseThrow(()->new PokemonException("Unable to find Pokemon"));
        pokemonService.deletePokemonById(pokemon.getId());
        return pokemon.getName()+" was removed successfully";
    }
}
