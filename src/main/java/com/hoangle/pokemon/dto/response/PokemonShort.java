package com.hoangle.pokemon.dto.response;

public class PokemonShort {

  private int pokemonId;

  private String name;

  private String image;

  public PokemonShort() {
  }

  public PokemonShort(int pokemonId, String name, String image) {
    this.pokemonId = pokemonId;
    this.name = name;
    this.image = image;
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
}
