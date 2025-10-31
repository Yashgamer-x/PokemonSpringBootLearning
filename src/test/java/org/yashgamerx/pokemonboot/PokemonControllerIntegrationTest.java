package org.yashgamerx.pokemonboot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.yashgamerx.pokemonboot.dao.PokemonRegion;
import org.yashgamerx.pokemonboot.dao.PokemonType;
import org.yashgamerx.pokemonboot.dto.PokemonDto;
import org.yashgamerx.pokemonboot.dto.PokemonRegionDto;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PokemonControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port+"/";
    }

    @BeforeEach
    public void setup() {
        var pokemonRegion = PokemonRegion.builder()
                .name("Kanto")
                .population(100)
                .build();
        restTemplate.postForEntity(baseUrl() + "region/add", pokemonRegion, PokemonRegionDto.class);
        var pokemonDto = PokemonDto.builder()
                .name("Pikachu")
                .ability("Lightning Rod")
                .level(7)
                .regionName("Kanto")
                .pokemonTypes(List.of(PokemonType.ELECTRIC))
                .build();
        restTemplate.postForEntity(baseUrl() + "pokemon/add", pokemonDto, PokemonDto.class);
    }

    @Test
    void testGetPokemonByNameWhereItIsOK() {
        var response = restTemplate.getForEntity(baseUrl() + "pokemon/search?name=Pikachu", PokemonDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testAddPokemonWhereItIsValid() {
        var newPokemon = PokemonDto.builder()
                .name("Charmander")
                .level(10)
                .ability("Blaze")
                .pokemonTypes(List.of(PokemonType.FIRE))
                .regionName("Kanto")
                .build();

        var response = restTemplate.postForEntity(baseUrl() + "pokemon/add", newPokemon, PokemonDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Charmander", response.getBody().name());
    }

    @Test
    void testUpdatePokemon() {
        var updatedPokemon = PokemonDto.builder()
                .name("Pikachu")
                .level(15)
                .ability("Static")
                .pokemonTypes(List.of(PokemonType.ELECTRIC))
                .regionName("Kanto")
                .build();

        restTemplate.put(baseUrl() + "pokemon/update", updatedPokemon);
        var response = restTemplate.getForEntity(baseUrl() + "pokemon/search?name=Pikachu", PokemonDto.class);
        assertNotNull(response.getBody());
        assertEquals(15, response.getBody().level());
    }

    @Test
    void testDeletePokemonById() {
        restTemplate.delete(baseUrl() + "pokemon/delete?name=Pikachu");
        var response = restTemplate.getForEntity(baseUrl() + "pokemon/search?name=Pikachu", PokemonDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}