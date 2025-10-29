package org.yashgamerx.pokemonboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yashgamerx.pokemonboot.dao.Pokemon;
import org.yashgamerx.pokemonboot.dto.PokemonDto;
import org.yashgamerx.pokemonboot.exception.PokemonException;
import org.yashgamerx.pokemonboot.exception.PokemonIdNotFoundException;
import org.yashgamerx.pokemonboot.exception.PokemonNameNotFoundException;
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
    public ResponseEntity<Pokemon> getPokemonByName(@RequestParam String name) {
        var pokemonOptional = pokemonService.findPokemonByName(name);
        var pokemon = pokemonOptional.orElseThrow(()->
                new PokemonNameNotFoundException(name)
        );
        log.info("Pokemon found with name {}",pokemon.getName());
        return ResponseEntity
                .ok()
                .body(pokemon);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pokemon> getPokemonById(@PathVariable String id) {
        var pokemonId = Integer.parseInt(id);
        var pokemonOptional = pokemonService.findPokemonById(pokemonId);
        var pokemon = pokemonOptional.orElseThrow(()->
                new PokemonIdNotFoundException(id)
        );
        log.info("Pokemon found with name {}",pokemon.getName());
        return ResponseEntity
                .ok()
                .body(pokemon);
    }


    @PostMapping("/add")
    public @ResponseBody String addPokemon(@RequestBody PokemonDto pokemonDto) {
        var pokemonRegionName = pokemonDto.regionName();
        var pokemonRegionOptional = pokemonRegionService.getPokemonRegionByName(pokemonRegionName);
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
        var pokemonOptional = pokemonService.findPokemonByName(pokemonDto.name());
        var pokemon = pokemonOptional.orElseThrow(()->new PokemonException("Unable to find Pokemon Region"));
        var pokemonRegionName = pokemonDto.regionName();
        var pokemonRegion = pokemonRegionService.getPokemonRegionByName(pokemonRegionName);
        pokemonService.updatePokemon(pokemonDto, pokemon, pokemonRegion);
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
