package com.hoangle.pokemon.models;

import javax.persistence.*;

@Entity
public class Pokemon {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int pokemonId;

  private String name;

  private String image;

  private int level;

  private int experience;

  @ManyToOne
  @JoinColumn(name="user_id", nullable = false)
  private User user;

  public Pokemon() {
  }

  public Pokemon(int pokemonId, String name, String image, int level, int experience, User user) {
    this.pokemonId = pokemonId;
    this.name = name;
    this.image = image;
    this.level = level;
    this.experience = experience;
    this.user = user;
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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
