package com.hoangle.pokemon.dto.request;

public class UpdatePokemonRequest {

  private int level;
  private int experience;

  public UpdatePokemonRequest() {
  }

  public UpdatePokemonRequest(int level, int experience) {
    this.level = level;
    this.experience = experience;
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
