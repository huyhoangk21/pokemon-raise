package com.hoangle.pokemon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.ExchangeStrategies;

@Configuration
public class WebClientConfig {

  private final ObjectMapper objectMapper;

  @Autowired
  public WebClientConfig(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Bean
  public WebClient webClient() {

    ExchangeStrategies strategies = ExchangeStrategies
        .builder()
        .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(20 * 1024 * 1024))
        .codecs(configurer -> configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper)))
        .build();

    return WebClient
        .builder()
        .exchangeStrategies(strategies)
        .build();
  }
}
