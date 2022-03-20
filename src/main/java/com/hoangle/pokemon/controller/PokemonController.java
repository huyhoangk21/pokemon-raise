package com.hoangle.pokemon.controller;

import com.hoangle.pokemon.dto.response.HttpResponse;
import com.hoangle.pokemon.dto.response.PokemonShort;
import com.hoangle.pokemon.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value ="v1/api/pokemon")
public class PokemonController {

  private final PokemonService pokemonService;

  @Autowired
  public PokemonController(PokemonService pokemonService) {
    this.pokemonService = pokemonService;
  }

  @PostMapping(value="/")
  @ResponseStatus(HttpStatus.CREATED)
  public void createPokemon(@RequestBody PokemonShort pokemonShort, HttpServletRequest request) {

  }

  @PutMapping(value="/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void updatePokemon(@RequestBody PokemonShort pokemonShort,
                            @PathVariable Long id,
                            HttpServletRequest request) {

  }

  @DeleteMapping(value="/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deletePokemon(@RequestBody PokemonShort pokemonShort,
                            @PathVariable Long id,
                            HttpServletRequest request) {

  }


  @GetMapping(value="/random")
  @ResponseStatus(HttpStatus.OK)
  public HttpResponse<PokemonShort> getRandom(HttpServletRequest request) {
    return pokemonService.getRandom(request);
  }

  @GetMapping(value="/pokedex")
  @ResponseStatus(HttpStatus.OK)
  public void getPokedex(HttpServletRequest request) {

  }

}
