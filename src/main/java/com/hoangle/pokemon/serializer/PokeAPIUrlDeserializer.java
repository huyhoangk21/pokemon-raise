package com.hoangle.pokemon.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hoangle.pokemon.dto.response.PokeAPIUrl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PokeAPIUrlDeserializer extends StdDeserializer<PokeAPIUrl> {

  public PokeAPIUrlDeserializer() {
    this(null);
  }

  public PokeAPIUrlDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public PokeAPIUrl deserialize(JsonParser jsonParser,
                                DeserializationContext deserializationContext) throws IOException, JacksonException {

    JsonNode productNode = jsonParser.getCodec().readTree(jsonParser);
    PokeAPIUrl pokeAPIUrl = new PokeAPIUrl();

    pokeAPIUrl.setCount(productNode.get("count").intValue());
    pokeAPIUrl.setNext(productNode.get("next").textValue());
    List<String> urls = StreamSupport
        .stream(productNode.get("results").spliterator(), true)
        .map(result -> result.get("url").textValue())
        .collect(Collectors.toList());

    pokeAPIUrl.setUrls(urls);
    return pokeAPIUrl;
  }
}
