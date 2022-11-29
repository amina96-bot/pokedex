package com.pokedex.pokedex.repository;

import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon,Long> {

    //retourne tous les pokemons ayant comme type au mons un des types de la liste des types: types
    @Query(value = "select distinct p from Pokemon p where exists (select 1 from p.types t where t in (:types))")
    List<Pokemon> findByTypeInList(@Param("types") List<Type> types);
}
