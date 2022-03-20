package com.hoangle.pokemon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hoangle.pokemon.dto.pokeAPI.PokeAPIUrls;
import com.hoangle.pokemon.dto.pokeAPI.PokeAPISummary;
import com.hoangle.pokemon.serializer.PokeAPIUrlsDeserializer;
import com.hoangle.pokemon.serializer.PokeAPISummaryDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JsonConfig {

  @Bean
  public ObjectMapper defaultMapper() {

    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(PokeAPISummary.class, new PokeAPISummaryDeserializer());
    module.addDeserializer(PokeAPIUrls.class, new PokeAPIUrlsDeserializer());
    objectMapper.registerModule(module);
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return objectMapper;
  }

}
