package org.yashgamerx.pokemonboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yashgamerx.pokemonboot.dao.Pokemon;
import org.yashgamerx.pokemonboot.dao.PokemonRegion;
import org.yashgamerx.pokemonboot.dto.PokemonDto;
import org.yashgamerx.pokemonboot.repo.PokemonRegionRepository;
import org.yashgamerx.pokemonboot.repo.PokemonRepository;
import org.yashgamerx.pokemonboot.service.PokemonRegionService;
import org.yashgamerx.pokemonboot.service.PokemonService;

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

    @PostMapping("/add")
    public @ResponseBody String addPokemon(@RequestBody PokemonDto pokemonDto) {
        var pokeRegion = pokemonRegionService.getPokemonRegionByDto(pokemonDto);
        var pokemon = pokemonService.createPokemon(pokemonDto, pokeRegion);
        pokemonService.savePokemon(pokemon);
        return pokemon.getName()+" was added successfully to "+pokeRegion.get().getName()+" Region";
    }

    @PutMapping("/update")
    public @ResponseBody String updatePokemon(@RequestBody PokemonDto pokemonDto){
        var pokemon = pokemonService.findPokemonByNameByDto(pokemonDto);
        PokemonRegion pokeRegion = null;
        if(pokemonDto.getAbility() != null && !pokemonDto.getAbility().isEmpty()){
            pokemon.setAbility(pokemonDto.getAbility());
        }
        if(pokemonDto.getLevel() != null && pokemonDto.getLevel()>0){
            pokemon.setLevel(pokemonDto.getLevel());
        }
        if(pokemonDto.getRegionName() != null && !pokemonDto.getRegionName().isEmpty()){
            pokeRegion =  pokemonRegionRepository.findPokemonRegionByName(pokemonDto.getRegionName());
        } else if( pokemonDto.getRegionId() != null) {
            pokeRegion = pokemonRegionRepository.findPokemonRegionById(pokemonDto.getRegionId());
        }
        if(pokeRegion != null){
            pokemon.setPokemonRegion(pokeRegion);
        }
        pokemonRepository.save(pokemon);
        return pokemon.getName()+" was updated successfully";
    }
}
