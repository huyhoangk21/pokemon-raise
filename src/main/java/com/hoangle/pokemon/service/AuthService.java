package com.hoangle.pokemon.service;

import com.hoangle.pokemon.dto.request.LoginRequest;
import com.hoangle.pokemon.dto.request.SignupRequest;
import com.hoangle.pokemon.dto.response.AuthData;
import com.hoangle.pokemon.dto.response.HttpResponse;
import com.hoangle.pokemon.exception.ResourceAlreadyExistsException;
import com.hoangle.pokemon.model.SecurityUser;
import com.hoangle.pokemon.model.User;
import com.hoangle.pokemon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthService {

  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  @Autowired
  public AuthService(JwtService jwtService,
                     UserRepository userRepository,
                     PasswordEncoder passwordEncoder,
                     AuthenticationManager authenticationManager) {
    this.jwtService = jwtService;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
  }

  public HttpResponse<Void> signup(SignupRequest signupRequest, HttpServletRequest request) {

    String username = signupRequest.getUsername();
    String password = signupRequest.getPassword();
    if (userRepository.existsByUsername(username)) {
      throw new ResourceAlreadyExistsException(String.format("User with username %s already existed", username));
    }


    User user = new User(username, passwordEncoder.encode(password));

    userRepository.save(user);

    return new HttpResponse<>(request.getRequestURI(),
                                  HttpStatus.CREATED,
                                  "User with username " + username + " has been created successfully.",
                                  null);
  }

  public HttpResponse<AuthData> login(LoginRequest loginRequest, HttpServletRequest request) {
    String username = loginRequest.getUsername();
    String password = loginRequest.getPassword();

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(username, password));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    SecurityUser user = (SecurityUser) authentication.getPrincipal();

    String token = jwtService.generateJwt(authentication);

    return new HttpResponse<AuthData>(request.getRequestURI(),
                                      HttpStatus.OK,
                                      "User with username " + username + " has been authenticated successfully.",
                                      new AuthData(user.getUser().getId(),
                                                   user.getUsername(),
                                                   token));
  }

}
