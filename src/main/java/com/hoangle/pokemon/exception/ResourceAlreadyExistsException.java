package com.hoangle.pokemon.exception;

public class ResourceAlreadyExistsException extends RuntimeException{

  public ResourceAlreadyExistsException(String message) {
    super(message);
  }
}
