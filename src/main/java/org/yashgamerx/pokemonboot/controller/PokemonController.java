package org.yashgamerx.pokemonboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yashgamerx.pokemonboot.dao.Pokemon;
import org.yashgamerx.pokemonboot.dao.PokemonRegion;
import org.yashgamerx.pokemonboot.dto.PokemonDto;
import org.yashgamerx.pokemonboot.repo.PokemonRegionRepository;
import org.yashgamerx.pokemonboot.repo.PokemonRepository;

import java.util.Optional;

@Controller
@RequestMapping("/pokemon")
public class PokemonController {
    private final PokemonRepository pokemonRepository;
    private final PokemonRegionRepository pokemonRegionRepository;
    public PokemonController(
            PokemonRepository pokemonRepository,
            PokemonRegionRepository pokemonRegionRepository
    ) {
        this.pokemonRepository = pokemonRepository;
        this.pokemonRegionRepository = pokemonRegionRepository;
    }

    @PostMapping("/add")
    public @ResponseBody String addPokemon(@RequestBody PokemonDto pokemonDto) {
        PokemonRegion pokeRegion=null;
        if(pokemonDto.getRegionName() != null && !pokemonDto.getRegionName().isEmpty()){
            pokeRegion =  pokemonRegionRepository.findPokemonRegionByName(pokemonDto.getRegionName());
        } else if( pokemonDto.getRegionId() != null) {
            pokeRegion = pokemonRegionRepository.findPokemonRegionById(pokemonDto.getRegionId());
        }
        if(pokeRegion == null){
            return "Invalid Region";
        }

        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setAbility(pokemonDto.getAbility());
        pokemon.setLevel(pokemonDto.getLevel());
        pokemon.setPokemonRegion(pokeRegion);

        pokemonRepository.save(pokemon);
        return pokemon.getName()+" was added successfully to "+pokeRegion.getName()+" Region";
    }

    @PutMapping("/update")
    public @ResponseBody String updatePokemon(@RequestBody PokemonDto pokemonDto){
        try{
            var pokemon = pokemonRepository.findPokemonByName(pokemonDto.getName());
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
        }catch(Exception e){
            return "Invalid Pokemon";
        }
    }
}
