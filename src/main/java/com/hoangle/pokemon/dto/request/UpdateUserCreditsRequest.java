package com.hoangle.pokemon.dto.request;

public class UpdateUserCreditsRequest {

  private int credits;

  public UpdateUserCreditsRequest() {
  }

  public UpdateUserCreditsRequest(int credits) {
    this.credits = credits;
  }

  public int getCredits() {
    return credits;
  }

  public void setCredits(int credits) {
    this.credits = credits;
  }
}
