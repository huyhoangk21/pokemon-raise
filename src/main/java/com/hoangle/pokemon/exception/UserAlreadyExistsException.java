package com.hoangle.pokemon.exception;

public class UserAlreadyExistsException extends RuntimeException{

  public UserAlreadyExistsException(String message) {
    super(message);
  }
}
