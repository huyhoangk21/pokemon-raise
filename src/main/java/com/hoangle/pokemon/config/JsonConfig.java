package com.hoangle.pokemon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hoangle.pokemon.dto.response.PokeAPIUrl;
import com.hoangle.pokemon.dto.response.PokemonShort;
import com.hoangle.pokemon.serializer.PokeAPIUrlDeserializer;
import com.hoangle.pokemon.serializer.PokemonShortDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JsonConfig {

  @Bean
  public ObjectMapper defaultMapper() {

    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(PokemonShort.class, new PokemonShortDeserializer());
    module.addDeserializer(PokeAPIUrl.class, new PokeAPIUrlDeserializer());
    objectMapper.registerModule(module);
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return objectMapper;
  }

}
