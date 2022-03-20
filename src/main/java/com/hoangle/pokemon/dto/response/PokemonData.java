package com.hoangle.pokemon.dto.response;

import com.hoangle.pokemon.model.Pokemon;

public class PokemonData {

  private Long id;

  private int pokemonId;

  private String name;

  private String image;

  private int level;

  private int experience;

  public PokemonData() {
  }

  public PokemonData(Pokemon pokemon) {
    this.id = pokemon.getId();
    this.pokemonId = pokemon.getPokemonId();
    this.name = pokemon.getName();
    this.image = pokemon.getImage();
    this.level = pokemon.getLevel();
    this.experience = pokemon.getExperience();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getPokemonId() {
    return pokemonId;
  }

  public void setPokemonId(int pokemonId) {
    this.pokemonId = pokemonId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getExperience() {
    return experience;
  }

  public void setExperience(int experience) {
    this.experience = experience;
  }
}
