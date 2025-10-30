package org.yashgamerx.pokemonboot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yashgamerx.pokemonboot.dao.Pokemon;
import org.yashgamerx.pokemonboot.dto.PokemonDto;
import org.yashgamerx.pokemonboot.exception.*;
import org.yashgamerx.pokemonboot.service.PokemonRegionService;
import org.yashgamerx.pokemonboot.service.PokemonService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pokemon")
public class PokemonController {
    private final PokemonRegionService pokemonRegionService;
    private final PokemonService pokemonService;

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
    public ResponseEntity<PokemonDto> getPokemonById(@PathVariable String id) {
        var pokemonId = Integer.parseInt(id);
        var pokemonOptional = pokemonService.findPokemonById(pokemonId);
        var pokemon = pokemonOptional.orElseThrow(()->
                new PokemonIdNotFoundException(id)
        );
        var pokemonDto = pokemonService.mapPokemonToDto(pokemon);
        log.info("Pokemon found with id {}",pokemonDto.name());
        return ResponseEntity
                .ok()
                .body(pokemonDto);
    }


    @PostMapping("/add")
    public ResponseEntity<PokemonDto> addPokemon(@Valid @RequestBody PokemonDto pokemonDto) {
        var pokemonRegionName = pokemonDto.regionName();
        var pokemonRegionOptional = pokemonRegionService.getPokemonRegionByName(pokemonRegionName);
        var pokemonRegion = pokemonRegionOptional.orElseThrow(()->{
            log.error("Pokemon region not found");
            return new PokemonRegionNotFoundException(pokemonRegionName);
        });
        var pokemon = pokemonService.createPokemon(pokemonDto, pokemonRegion);
        pokemonService.savePokemon(pokemon);
        log.info("Pokemon Added Successfully");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pokemonDto);
    }


    @PutMapping("/update")
    public String updatePokemon(@Valid @RequestBody PokemonDto pokemonDto){
        var pokemonName = pokemonDto.name();
        var pokemonOptional = pokemonService.findPokemonByName(pokemonName);
        var pokemon = pokemonOptional.orElseThrow(()->new PokemonNotFoundException("Unable to find Pokemon Region"));
        var pokemonRegionName = pokemonDto.regionName();
        var pokemonRegion = pokemonRegionService.getPokemonRegionByName(pokemonRegionName);
        pokemonService.updatePokemon(pokemonDto, pokemon, pokemonRegion);
        pokemonService.savePokemon(pokemon);
        return pokemon.getName()+" was updated successfully";
    }

    @DeleteMapping("/delete")
    public String deletePokemon(@RequestParam String name){
        var pokemonOptional = pokemonService.findPokemonByName(name);
        var pokemon = pokemonOptional.orElseThrow(()->new PokemonNotFoundException("Unable to find Pokemon"));
        pokemonService.deletePokemonById(pokemon.getId());
        return pokemon.getName()+" was removed successfully";
    }

}
