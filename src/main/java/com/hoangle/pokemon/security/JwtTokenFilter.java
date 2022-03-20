package com.hoangle.pokemon.security;

import com.hoangle.pokemon.service.JwtService;
import com.hoangle.pokemon.service.SecurityUserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtTokenFilter extends OncePerRequestFilter {

  private final SecurityUserService securityUserService;
  private final JwtService jwtService;

  public JwtTokenFilter(SecurityUserService securityUserService, JwtService jwtService) {
    this.securityUserService = securityUserService;
    this.jwtService = jwtService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {

    try {
      String token = parseJwt(request);
      if (token != null && jwtService.isJwtValid(token)) {
        String username = jwtService.getUsernameFromJwt(token);
        UserDetails user = securityUserService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user,
                                                                                null,
                                                                                Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      logger.error("Cannot set authentication {}", e);
    }
    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (StringUtils.hasText(authHeader) && authHeader.startsWith(jwtService.getPrefix() + " ")) {
      return authHeader.substring(7, authHeader.length());
    }
    return null;
  }
}
