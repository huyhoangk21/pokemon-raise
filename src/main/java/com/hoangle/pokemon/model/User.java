package com.hoangle.pokemon.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class User extends Auditable{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String username;

  private  String password;

  private int credits;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Pokemon> pokemon;

  public User() {
  }

  public User(String username, String password) {
    this.username = username;
    this.password = password;
    this.credits = 10000;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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
