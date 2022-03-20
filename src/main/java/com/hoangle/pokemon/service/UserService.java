package com.hoangle.pokemon.service;

import com.hoangle.pokemon.dto.request.UpdateUserCreditsRequest;
import com.hoangle.pokemon.dto.response.HttpResponse;
import com.hoangle.pokemon.dto.response.UserData;
import com.hoangle.pokemon.exception.ForbiddenActionException;
import com.hoangle.pokemon.model.SecurityUser;
import com.hoangle.pokemon.model.User;
import com.hoangle.pokemon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public HttpResponse<UserData> updateUserCredits(UpdateUserCreditsRequest updateUserCreditsRequest,
                                                  HttpServletRequest request) {

    SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    User user = securityUser.getUser();
    user.setCredits(updateUserCreditsRequest.getCredits());

    userRepository.save(user);

    UserData userData = new UserData(user.getId(), user.getUsername(), user.getCredits());

    return new HttpResponse<UserData>(request.getRequestURI(),
                                      HttpStatus.OK,
                                      "User " + user.getUsername() +"'s credits has been updated.",
                                      userData);

  }

  public HttpResponse<UserData> getMyself(HttpServletRequest request) {

    SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = securityUser.getUser();

    UserData userData = new UserData(user.getId(), user.getUsername(), user.getCredits(), user.getPokemon());

    return new HttpResponse<UserData>(request.getRequestURI(),
                                      HttpStatus.OK,
                                      "User " + user.getUsername() +" has been retrieved.",
                                      userData);
  }
}
