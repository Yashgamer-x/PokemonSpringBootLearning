package org.yashgamerx.pokemonboot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yashgamerx.pokemonboot.dao.Pokemon;
import org.yashgamerx.pokemonboot.dto.PokemonDto;
import org.yashgamerx.pokemonboot.repo.PokemonRepository;
import org.yashgamerx.pokemonboot.service.PokemonService;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private PokemonService pokemonService;

    public PokemonServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindPokemonById() {
        Pokemon pokemon = Pokemon.builder()
                .id(1)
                .name("Pikachu")
                .level(10)
                .ability("Lightning Dash")
                .build();

        Mockito.when(pokemonService.findPokemonById(pokemon.getId()))
                .thenReturn(Optional.of(pokemon));

        var pokemonOptional = pokemonService.findPokemonById(1);
        Assertions.assertTrue(pokemonOptional.isPresent());
        Assertions.assertEquals(pokemon.getName(), pokemonOptional.get().getName());
        Assertions.assertEquals(pokemon.getId(), pokemonOptional.get().getId());
        Assertions.assertEquals(pokemon.getLevel(), pokemonOptional.get().getLevel());
        Assertions.assertEquals(pokemon.getAbility(), pokemonOptional.get().getAbility());
    }
    
}
