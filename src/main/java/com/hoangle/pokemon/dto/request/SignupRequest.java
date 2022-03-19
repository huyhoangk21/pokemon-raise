package com.hoangle.pokemon.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignupRequest {

  @NotBlank(message = "Username is required")
  @Size(min=6, max=15, message = "Username must be between 6-15 characters")
  private String username;

  @NotBlank(message = "Password is required")
  @Size(min=6, max=15, message = "Password must be between 6-15 characters")
  private String password;

  public SignupRequest() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
