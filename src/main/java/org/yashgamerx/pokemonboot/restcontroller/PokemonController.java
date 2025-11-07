package org.yashgamerx.pokemonboot.restcontroller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<PokemonDto> searchPokemon(@RequestParam String name) {
        if (name.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        var pokemonOptional = pokemonService.findPokemonByName(name);
        var pokemon = pokemonOptional.orElseThrow(() ->
                new PokemonNameNotFoundException(name)
        );
        var pokemonDto = pokemonService.mapPokemonToDto(pokemon);
        log.info("Pokemon found: {}", pokemonDto.name());
        return ResponseEntity.ok(pokemonDto);
    }


    @PostMapping("/add")
    public ResponseEntity<PokemonDto> addPokemon(@Valid @RequestBody PokemonDto pokemonDto) {
        var pokemonRegionName = pokemonDto.regionName();
        var pokemonRegionOptional = pokemonRegionService.getPokemonRegionByName(pokemonRegionName);
        var pokemonRegion = pokemonRegionOptional.orElseThrow(()->{
            log.error("Pokemon region not found");
            return new PokemonRegionNameNotFoundNotFoundException(pokemonRegionName);
        });
        var pokemon = pokemonService.createPokemon(pokemonDto, pokemonRegion);
        pokemonService.savePokemon(pokemon);
        log.info("Pokemon Added Successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(pokemonDto);
    }


    @PutMapping("/update")
    public ResponseEntity<PokemonDto> updatePokemon(@Valid @RequestBody PokemonDto pokemonDto){
        var pokemonName = pokemonDto.name();
        var pokemonOptional = pokemonService.findPokemonByName(pokemonName);
        var pokemon = pokemonOptional.orElseThrow(()->new PokemonNameNotFoundException(pokemonName));
        var pokemonRegionName = pokemonDto.regionName();
        var pokemonRegion = pokemonRegionService.getPokemonRegionByName(pokemonRegionName);
        pokemonService.updatePokemon(pokemonDto, pokemon, pokemonRegion);
        pokemonService.savePokemon(pokemon);
        return ResponseEntity.ok(pokemonDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletePokemon(@RequestParam String name){
        var pokemonOptional = pokemonService.findPokemonByName(name);
        var pokemon = pokemonOptional.orElseThrow(()->new PokemonNameNotFoundException(name));
        pokemonService.deletePokemonById(pokemon.getId());
        return ResponseEntity.noContent().build();
    }

}
