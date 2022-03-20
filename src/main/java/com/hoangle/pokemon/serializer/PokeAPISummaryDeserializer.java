package com.hoangle.pokemon.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hoangle.pokemon.dto.pokeAPI.PokeAPISummary;

import java.io.IOException;

public class PokeAPISummaryDeserializer extends StdDeserializer<PokeAPISummary> {

  public PokeAPISummaryDeserializer() {
    this(null);
  }

  public PokeAPISummaryDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public PokeAPISummary deserialize(JsonParser jsonParser,
                                    DeserializationContext deserializationContext) throws IOException, JacksonException {

    JsonNode productNode = jsonParser.getCodec().readTree(jsonParser);
    PokeAPISummary pokemon = new PokeAPISummary();

    pokemon.setPokemonId(productNode.get("id").intValue());
    pokemon.setName(productNode.get("name").textValue());
    pokemon.setImage(productNode.get("sprites").get("other").get("official-artwork").get("front_default").textValue());

    return pokemon;
  }
}
