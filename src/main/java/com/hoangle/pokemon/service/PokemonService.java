package com.hoangle.pokemon.service;

import com.hoangle.pokemon.dto.response.HttpResponse;
import com.hoangle.pokemon.dto.response.PokeAPIUrl;
import com.hoangle.pokemon.dto.response.PokemonShort;
import com.hoangle.pokemon.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PokemonService {

  private final WebClient webClient;
  private final PokemonRepository pokemonRepository;

  @Autowired
  public PokemonService(WebClient webClient, PokemonRepository pokemonRepository) {
    this.webClient = webClient;
    this.pokemonRepository = pokemonRepository;
  }

  public HttpResponse<PokemonShort> getRandom(HttpServletRequest request) {

    List<String> urls = getEntirePokeAPIUrls("?limit=200").expand(response -> {
      if (response.getNext() == null) {
        return Mono.empty();
      }
      return getEntirePokeAPIUrls(response.getNext());
    }).flatMap(response -> Flux.fromIterable(response.getUrls())).collectList().block();

    int randomIndex
        = ThreadLocalRandom.current().nextInt(urls.size() + 1);

    PokemonShort pokemonShort = webClient
        .get()
        .uri(urls.get(randomIndex))
        .retrieve()
        .bodyToMono(PokemonShort.class)
        .block();

    return new HttpResponse<>(request.getRequestURI(),
                              HttpStatus.OK,
                              "A random pokemon generated.",
                              pokemonShort);
  }

  private Mono<PokeAPIUrl> getEntirePokeAPIUrls(String next) {
    return webClient.get().uri(next).retrieve().bodyToMono(PokeAPIUrl.class);
  }

}
