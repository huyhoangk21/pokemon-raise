package com.hoangle.pokemon.dto.response;

import java.util.List;

public class PokeAPIUrl {

  private int count;
  private String next;
  private List<String> urls;

  public PokeAPIUrl() {
  }

  public PokeAPIUrl(int count, String next, List<String> urls) {
    this.count = count;
    this.next = next;
    this.urls = urls;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public List<String> getUrls() {
    return urls;
  }

  public void setUrls(List<String> urls) {
    this.urls = urls;
  }

  public String getNext() {
    return next;
  }

  public void setNext(String next) {
    this.next = next;
  }
}
