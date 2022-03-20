package com.hoangle.pokemon.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hoangle.pokemon.dto.response.PokemonShort;

import java.io.IOException;

public class PokemonShortDeserializer extends StdDeserializer<PokemonShort> {

  public PokemonShortDeserializer() {
    this(null);
  }

  public PokemonShortDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public PokemonShort deserialize(JsonParser jsonParser,
                             DeserializationContext deserializationContext) throws IOException, JacksonException {

    JsonNode productNode = jsonParser.getCodec().readTree(jsonParser);
    PokemonShort pokemon = new PokemonShort();

    pokemon.setPokemonId(productNode.get("id").intValue());
    pokemon.setName(productNode.get("name").textValue());
    pokemon.setImage(productNode.get("sprites").get("other").get("official-artwork").get("front_default").textValue());

    return pokemon;
  }
}
