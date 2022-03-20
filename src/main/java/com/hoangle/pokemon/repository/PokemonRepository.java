package com.hoangle.pokemon.repository;

import com.hoangle.pokemon.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

  Optional<Pokemon> findById(Long id);

  void deleteById(Long id);
}
