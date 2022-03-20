package com.hoangle.pokemon.dto.request;


public class CreatePokemonRequest {

  private int pokemonId;


  public CreatePokemonRequest() {
  }

  public CreatePokemonRequest(int pokemonId) {
    this.pokemonId = pokemonId;
  }

  public int getPokemonId() {
    return pokemonId;
  }

  public void setPokemonId(int pokemonId) {
    this.pokemonId = pokemonId;
  }
}
