package com.hoangle.pokemon.service;

import com.hoangle.pokemon.dto.request.CreatePokemonRequest;
import com.hoangle.pokemon.dto.request.UpdatePokemonRequest;
import com.hoangle.pokemon.dto.response.HttpResponse;
import com.hoangle.pokemon.dto.pokeAPI.PokeAPIUrls;
import com.hoangle.pokemon.dto.pokeAPI.PokeAPISummary;
import com.hoangle.pokemon.dto.response.PokemonData;
import com.hoangle.pokemon.exception.ForbiddenActionException;
import com.hoangle.pokemon.exception.PokeAPIException;
import com.hoangle.pokemon.exception.ResourceNotFoundException;
import com.hoangle.pokemon.model.Pokemon;
import com.hoangle.pokemon.model.SecurityUser;
import com.hoangle.pokemon.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class PokemonService {

  private final WebClient webClient;
  private final PokemonRepository pokemonRepository;

  @Autowired
  public PokemonService(WebClient webClient, PokemonRepository pokemonRepository) {
    this.webClient = webClient;
    this.pokemonRepository = pokemonRepository;
  }

  public HttpResponse<PokemonData> createPokemon(CreatePokemonRequest createPokemonRequest,
                                             HttpServletRequest request) {
    final String url = "https://pokeapi.co/api/v2/pokemon/";

    SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    PokeAPISummary pokeAPISummary = webClient
        .get()
        .uri(url + createPokemonRequest.getPokemonId())
        .retrieve()
        .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                  response -> Mono.error(new PokeAPIException("Client or Server Error from PokeAPI")))
        .bodyToMono(PokeAPISummary.class)
        .block();


    assert pokeAPISummary != null;
    Pokemon pokemon = new Pokemon(pokeAPISummary.getPokemonId(),
                                  pokeAPISummary.getName(),
                                  pokeAPISummary.getImage(),
                                  1,
                                  100,
                                  user.getUser());

    pokemonRepository.save(pokemon);

    PokemonData pokemonData = new PokemonData(pokemon);

    return new HttpResponse<>(request.getRequestURI(),
                              HttpStatus.CREATED,
                              "Pokemon with id " + pokemonData.getId() + " has been created.",
                              pokemonData);
  }

  public HttpResponse<Void> deletePokemon(Long id, HttpServletRequest request) {

    Pokemon pokemon = pokemonRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Pokemon with id " + id + " does not exist."));

    SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (!pokemon.getUser().getId().equals(user.getUser().getId())) {
      throw new ForbiddenActionException("User with username "
                                             + user.getUsername()
                                             + " is unauthorized to perform the request.");
    }

    pokemonRepository.deleteById(id);

    return new HttpResponse<>(request.getRequestURI(),
                              HttpStatus.OK,
                              "Pokemon with id " + id + " has been deleted.",
                              null);
  }

  public HttpResponse<PokemonData> updatePokemonExperience(UpdatePokemonRequest updatePokemonRequest,
                                                           Long id,
                                                           HttpServletRequest request) {
    Pokemon pokemon = pokemonRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Pokemon with id " + id + " does not exist."));

    SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (!pokemon.getUser().getId().equals(user.getUser().getId())) {
      throw new ForbiddenActionException("User with username "
                                             + user.getUsername()
                                             + " is unauthorized to perform the request.");
    }

    pokemon.setLevel(updatePokemonRequest.getLevel());
    pokemon.setExperience(updatePokemonRequest.getExperience());

    pokemonRepository.save(pokemon);

    PokemonData pokemonData = new PokemonData(pokemon);

    return new HttpResponse<>(request.getRequestURI(),
                              HttpStatus.CREATED,
                              "Pokemon with id " + pokemonData.getId() + " has been updated.",
                              pokemonData);

  }


  public HttpResponse<PokeAPISummary> getRandom(HttpServletRequest request) {

    final String url = "https://pokeapi.co/api/v2/pokemon?limit=200";
    List<String> urls = getEntirePokeAPIUrls(url)
        .expand(response -> {
      if (response.getNext() == null) {
        return Mono.empty();
      }
      return getEntirePokeAPIUrls(response.getNext());
    }).flatMap(response -> Flux.fromIterable(response.getUrls())).collectList().block();


    assert urls != null;
    int randomIndex = ThreadLocalRandom.current().nextInt(urls.size() + 1);


    PokeAPISummary pokeAPISummary = webClient
        .get()
        .uri(urls.get(randomIndex))
        .retrieve()
        .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                  response -> Mono.error(new PokeAPIException("Client or Server Error from PokeAPI")))
        .bodyToMono(PokeAPISummary.class)
        .block();

    return new HttpResponse<>(request.getRequestURI(),
                              HttpStatus.OK,
                              "A random pokemon has been generated.",
                              pokeAPISummary);
  }

  private Mono<PokeAPIUrls> getEntirePokeAPIUrls(String next) {
    return webClient.get()
                    .uri(next)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                              response -> Mono.error(new PokeAPIException("Client or Server Error from PokeAPI")))
                    .bodyToMono(PokeAPIUrls.class);
  }

}
