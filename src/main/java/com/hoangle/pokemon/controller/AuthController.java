package com.hoangle.pokemon.controller;

import com.hoangle.pokemon.dto.request.LoginRequest;
import com.hoangle.pokemon.dto.request.SignupRequest;
import com.hoangle.pokemon.dto.response.Auth;
import com.hoangle.pokemon.dto.response.HttpResponse;
import com.hoangle.pokemon.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "v1/api/auth")
public class AuthController {

  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public HttpResponse<Auth> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
    return authService.login(loginRequest, request);
  }

  @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public HttpResponse<Void> signup(@Valid @RequestBody SignupRequest signupRequest, HttpServletRequest request) {
    return authService.signup(signupRequest, request);
  }
}
