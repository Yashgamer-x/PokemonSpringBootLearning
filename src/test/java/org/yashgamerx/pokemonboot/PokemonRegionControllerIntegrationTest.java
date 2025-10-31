package org.yashgamerx.pokemonboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.yashgamerx.pokemonboot.dao.PokemonRegion;
import org.yashgamerx.pokemonboot.dto.PokemonRegionDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class PokemonRegionControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port+"/";
    }

    @Test
    void testAddPokemonRegionWhereItIsValid(){
        var pokemonRegion = PokemonRegion.builder()
                .name("Kanto")
                .population(100)
                .build();
        var response = restTemplate.postForEntity(baseUrl() + "region/add", pokemonRegion, PokemonRegionDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetPokemonRegionWhereItIsValid(){
        var pokemonRegion = PokemonRegion.builder()
                .name("Kanto")
                .population(100)
                .build();
        var response = restTemplate.postForEntity(baseUrl() + "region/add", pokemonRegion, PokemonRegionDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

}
