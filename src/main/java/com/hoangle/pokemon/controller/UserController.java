package com.hoangle.pokemon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="v1/api/user")
public class UserController {

  @PutMapping(value="/{id}")
  public void updateUserCredits() {

  }

  @GetMapping(value="/me")
  public void get() {

  }



}
