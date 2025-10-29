package org.yashgamerx.pokemonboot.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yashgamerx.pokemonboot.dao.PokemonRegion;
import org.yashgamerx.pokemonboot.dto.PokemonRegionDto;
import org.yashgamerx.pokemonboot.exception.PokemonRegionNotFoundException;
import org.yashgamerx.pokemonboot.service.PokemonRegionService;

@RestController
@RequestMapping("/region")
public class PokemonRegionController {
    private final PokemonRegionService pokemonRegionService;

    public PokemonRegionController(PokemonRegionService pokemonRegionService) {
        this.pokemonRegionService = pokemonRegionService;
    }

    @PostMapping("/add")
    public ResponseEntity<PokemonRegion> add(@Valid @RequestBody PokemonRegionDto pokemonRegionDto) {
        var pokemonRegion = pokemonRegionService.convertPokemonRegionDtoToPokemonRegion(pokemonRegionDto);
        pokemonRegionService.savePokemonRegion(pokemonRegion);
        return ResponseEntity.ok()
                .body(pokemonRegion);
    }

    @GetMapping("/search")
    public ResponseEntity<PokemonRegion> getPokemonRegion(@RequestParam String name) {
        var pokemonRegionOptional = pokemonRegionService.getPokemonRegionByName(name);
        var pokemonRegion = pokemonRegionOptional.orElseThrow(()->
                new PokemonRegionNotFoundException(name)
        );
        return ResponseEntity.ok(pokemonRegion);
    }

    @PutMapping("/update")
    public ResponseEntity<PokemonRegion> update(@Valid @RequestBody PokemonRegionDto pokemonRegionDto) {
        var pokemonRegionName = pokemonRegionDto.name();
        var pokemonRegionOptional = pokemonRegionService.getPokemonRegionByName(pokemonRegionName);
        var pokemonRegion = pokemonRegionOptional.orElseThrow(()->
                new PokemonRegionNotFoundException(pokemonRegionName)
        );
        pokemonRegionService.updatePokemon(pokemonRegion, pokemonRegionDto);
        return ResponseEntity.ok(pokemonRegion);
    }

}
