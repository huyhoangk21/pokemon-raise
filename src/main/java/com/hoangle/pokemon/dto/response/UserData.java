package com.hoangle.pokemon.dto.response;

import com.hoangle.pokemon.model.Pokemon;

import java.util.List;

public class UserData {

  private Long id;

  private String username;

  private int credits;

  private List<Pokemon> pokemon;

  public UserData() {
  }

  public UserData(Long id, String username, int credits) {
    this.id = id;
    this.username = username;
    this.credits = credits;
  }

  public UserData(Long id, String username, int credits, List<Pokemon> pokemon) {
    this.id = id;
    this.username = username;
    this.credits = credits;
    this.pokemon = pokemon;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getCredits() {
    return credits;
  }

  public void setCredits(int credits) {
    this.credits = credits;
  }

  public List<Pokemon> getPokemon() {
    return pokemon;
  }

  public void setPokemon(List<Pokemon> pokemon) {
    this.pokemon = pokemon;
  }
}
