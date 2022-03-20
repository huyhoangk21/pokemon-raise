package com.hoangle.pokemon.dto.request;

public class UpdatePokemonRequest {


  private int experience;

  public UpdatePokemonRequest() {
  }

  public UpdatePokemonRequest( int experience) {
    this.experience = experience;
  }

  public int getExperience() {
    return experience;
  }

  public void setExperience(int experience) {
    this.experience = experience;
  }


}
