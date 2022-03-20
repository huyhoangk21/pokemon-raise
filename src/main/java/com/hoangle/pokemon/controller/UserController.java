package com.hoangle.pokemon.controller;

import com.hoangle.pokemon.dto.request.UpdateUserCreditsRequest;
import com.hoangle.pokemon.dto.response.HttpResponse;
import com.hoangle.pokemon.dto.response.UserData;
import com.hoangle.pokemon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value="v1/api/user")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping(value="/credits")
  public HttpResponse<UserData> updateUserCredits(@RequestBody UpdateUserCreditsRequest updateUserCreditsRequest,
                                                  HttpServletRequest request) {
    return userService.updateUserCredits(updateUserCreditsRequest, request);
  }

  @GetMapping(value="/me")
  public HttpResponse<UserData> getMyself(HttpServletRequest request) {
    return userService.getMyself(request);
  }



}
