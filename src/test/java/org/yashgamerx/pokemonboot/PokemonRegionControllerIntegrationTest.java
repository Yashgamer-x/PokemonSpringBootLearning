package org.yashgamerx.pokemonboot;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.yashgamerx.pokemonboot.dto.PokemonRegionDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext( classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD )
public class PokemonRegionControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    public String baseUrl() {
        return "http://localhost:"+port+"/region";
    }

    @BeforeEach
    public void setUp(){
        var pokemonRegion = PokemonRegionDto.builder()
                .name("Kanto")
                .population(200)
                .build();
        restTemplate.postForEntity(baseUrl()+"/add", pokemonRegion, PokemonRegionDto.class);
    }

    @Test
    public void testAddPokemonRegion() {
        var pokemonRegionDto = PokemonRegionDto.builder()
                .name("Johto")
                .population(500)
                .build();
        Assertions.assertNotNull(pokemonRegionDto);

        var pokemonRegionDtoResponseEntity = restTemplate.postForEntity(baseUrl()+"/add", pokemonRegionDto, PokemonRegionDto.class);
        Assertions.assertEquals(HttpStatus.CREATED, pokemonRegionDtoResponseEntity.getStatusCode());

        var pokemonRegionDtoResponseEntityBodyUnwrapped = pokemonRegionDtoResponseEntity.getBody();
        Assertions.assertNotNull(pokemonRegionDtoResponseEntityBodyUnwrapped);
        Assertions.assertEquals(pokemonRegionDto.name(),pokemonRegionDtoResponseEntityBodyUnwrapped.name());
        Assertions.assertEquals(pokemonRegionDto.population(),pokemonRegionDtoResponseEntityBodyUnwrapped.population());
    }

    @Test
    public void testGetPokemonRegionByPopulationFail(){
        var pokemonRegionDtoResponseEntity = restTemplate.getForEntity(baseUrl()+"/search?population=100", PokemonRegionDto.class);
        Assertions.assertNotEquals(HttpStatus.OK, pokemonRegionDtoResponseEntity.getStatusCode());
    }

    @Test
    public void testGetPokemonRegionByNamePass(){
        var pokemonRegionDtoResponseEntity = restTemplate.getForEntity(baseUrl()+"/search?name=Kanto", PokemonRegionDto.class);
        Assertions.assertEquals(HttpStatus.OK, pokemonRegionDtoResponseEntity.getStatusCode());

        var pokemonRegionDtoResponseEntityBodyUnwrapped = pokemonRegionDtoResponseEntity.getBody();
        Assertions.assertNotNull(pokemonRegionDtoResponseEntityBodyUnwrapped);
        Assertions.assertEquals("Kanto",pokemonRegionDtoResponseEntityBodyUnwrapped.name());
        Assertions.assertEquals(200, pokemonRegionDtoResponseEntityBodyUnwrapped.population());
    }

    @Test
    public void testUpdatePokemonRegion(){
        var pokemonRegionDto = PokemonRegionDto.builder()
                .name("Kanto")
                .population(300)
                .build();
        Assertions.assertNotNull(pokemonRegionDto);

        var httpEntity = new HttpEntity<>(pokemonRegionDto);
        Assertions.assertNotNull(httpEntity);

        var pokemonRegionDtoResponseEntity = restTemplate.exchange(baseUrl()+"/update", HttpMethod.PUT, httpEntity, PokemonRegionDto.class);
        Assertions.assertEquals(HttpStatus.OK, pokemonRegionDtoResponseEntity.getStatusCode());

        var pokemonRegionDtoResponseEntityBodyUnwrapped = pokemonRegionDtoResponseEntity.getBody();
        Assertions.assertNotNull(pokemonRegionDtoResponseEntityBodyUnwrapped);
        Assertions.assertEquals(pokemonRegionDto.name(),pokemonRegionDtoResponseEntityBodyUnwrapped.name());
        Assertions.assertEquals(pokemonRegionDto.population(),pokemonRegionDtoResponseEntityBodyUnwrapped.population());
    }

}
