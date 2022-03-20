package com.hoangle.pokemon.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoangle.pokemon.dto.response.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class AuthEntryPointExceptionHandler implements AuthenticationEntryPoint {


  private final ObjectMapper objectMapper;

  @Autowired
  public AuthEntryPointExceptionHandler(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void commence(HttpServletRequest request,
                       HttpServletResponse response,
                       AuthenticationException authException) throws IOException, ServletException {


    HttpResponse<Void> httpResponse =
        new HttpResponse<>(request.getRequestURI(),
                           HttpStatus.UNAUTHORIZED,
                           "User is unauthenticated",
                           null);

    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    objectMapper.writeValue(response.getOutputStream(), httpResponse);
  }
}
