package com.pokedex.pokedex.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "pokemon")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pokemon {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Numéro obligatoire")
    @Column(name = "numero")
    private String numero;

    @NotBlank(message = "Nom obligatoire")
    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "url_image")
    private String urlImage;

    @Column(name = "points_vie")
    private String pointsVie;

    //liste des types
    @ElementCollection
    @CollectionTable(name = "pokemon_types", joinColumns = @JoinColumn(name = "pokemon_id"))
    @Column(name = "type")
    private List<Type> types;

    //liste des attaques
    @ManyToMany
    @JoinTable(name = "pokemon_attaque",
            joinColumns = @JoinColumn(name = "pokemon_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "attaque_id", referencedColumnName = "id"))

    private List<Attaque> attaques;


    //liste des evolutions vers d'autres pokemons
    @ManyToMany
    @JoinTable(name = "pokemon_evolution_pokemon",
            joinColumns = @JoinColumn(name = "pokemon_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "evolution_pokemon_id", referencedColumnName = "id"))
    private List<Pokemon> evolutionPokemons ;
}
