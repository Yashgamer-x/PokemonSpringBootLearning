package org.yashgamerx.pokemonboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yashgamerx.pokemonboot.dao.Pokemon;
import org.yashgamerx.pokemonboot.dto.PokemonDto;
import org.yashgamerx.pokemonboot.exception.PokemonException;
import org.yashgamerx.pokemonboot.exception.PokemonRegionException;
import org.yashgamerx.pokemonboot.service.PokemonRegionService;
import org.yashgamerx.pokemonboot.service.PokemonService;

@Slf4j
@Controller
@RequestMapping("/pokemon")
public class PokemonController {
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
    public ResponseEntity<Pokemon> getPokemon(@RequestParam String name) {
        var pokemonOptional = pokemonService.findPokemonByName(name);
        Pokemon pokemon;
        if (pokemonOptional.isPresent()) {
            pokemon = pokemonOptional.get();
        }else{
            log.error("Pokemon not found"+" Request made by username: ");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("Pokemon found with name {}",pokemon.getName());
        return ResponseEntity
                .ok()
                .body(pokemon);
    }


    @PostMapping("/add")
    public @ResponseBody String addPokemon(@RequestBody PokemonDto pokemonDto) {
        var pokemonRegionOptional = pokemonRegionService.getPokemonRegionByDto(pokemonDto);
        var pokemonRegion = pokemonRegionOptional.orElseThrow(()->{
            log.error("Pokemon region not found");
            return new PokemonRegionException("Unable to find Pokemon Region");
        });
        var pokemon = pokemonService.createPokemon(pokemonDto, pokemonRegion);
        pokemonService.savePokemon(pokemon);
        log.info("Pokemon Added Successfully");
        return pokemon.getName()+" was added successfully to "+pokemonRegion.getName()+" Region";
    }


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
