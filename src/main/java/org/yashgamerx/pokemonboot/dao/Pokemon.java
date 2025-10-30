package org.yashgamerx.pokemonboot.dao;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer level;
    private String ability;
    @ElementCollection(targetClass = PokemonType.class)
    @Enumerated(EnumType.STRING)
    private List<PokemonType> types;
    @ManyToOne
    private PokemonRegion pokemonRegion;
}
