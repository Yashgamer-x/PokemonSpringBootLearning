package org.yashgamerx.pokemonboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yashgamerx.pokemonboot.dao.PokemonRegion;
import org.yashgamerx.pokemonboot.repo.PokemonRegionRepository;

@Controller
@RequestMapping("/region")
public class PokemonRegionController {
    private final PokemonRegionRepository pokemonRegionRepository;

    public PokemonRegionController(PokemonRegionRepository pokemonRegionRepository) {
        this.pokemonRegionRepository = pokemonRegionRepository;
    }

    @PostMapping("/add")
    public @ResponseBody String add(@RequestBody PokemonRegion pokemonRegion) {
        pokemonRegionRepository.save(pokemonRegion);
        return pokemonRegion.getName()+" is added successfully";
    }
}
