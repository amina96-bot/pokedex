package com.pokedex.pokedex.repository;

import com.pokedex.pokedex.model.Attaque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttaqueRepository extends JpaRepository<Attaque,Long> {
}
