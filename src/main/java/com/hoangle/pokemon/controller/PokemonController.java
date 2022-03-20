package com.hoangle.pokemon.controller;

import com.hoangle.pokemon.dto.request.CreatePokemonRequest;
import com.hoangle.pokemon.dto.request.UpdatePokemonRequest;
import com.hoangle.pokemon.dto.response.HttpResponse;
import com.hoangle.pokemon.dto.pokeAPI.PokeAPISummary;
import com.hoangle.pokemon.dto.response.PokemonData;
import com.hoangle.pokemon.model.Pokemon;
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

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public HttpResponse<PokemonData> createPokemon(@RequestBody CreatePokemonRequest createPokemonRequest,
                                                 HttpServletRequest request) {
    return pokemonService.createPokemon(createPokemonRequest, request);
  }

  @PostMapping(value="/{id}/experience")
  @ResponseStatus(HttpStatus.OK)
  public HttpResponse<PokemonData> updatePokemonExperience(@RequestBody UpdatePokemonRequest updatePokemonRequest,
                                                           @PathVariable Long id,
                                                           HttpServletRequest request) {

    return pokemonService.updatePokemonExperience( updatePokemonRequest, id, request);
  }

  @DeleteMapping(value="/{id}")
  @ResponseStatus(HttpStatus.OK)
  public HttpResponse<Void> deletePokemon(@PathVariable Long id,
                                          HttpServletRequest request) {
    return pokemonService.deletePokemon(id, request);
  }

  @GetMapping(value="/random")
  @ResponseStatus(HttpStatus.OK)
  public HttpResponse<PokeAPISummary> getRandom(HttpServletRequest request) {
    return pokemonService.getRandom(request);
  }

  @GetMapping(value="/pokedex")
  @ResponseStatus(HttpStatus.OK)
  public void getPokedex(HttpServletRequest request) {

  }

}
