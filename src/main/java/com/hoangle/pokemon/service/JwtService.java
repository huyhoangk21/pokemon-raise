package com.hoangle.pokemon.service;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@ConfigurationProperties(prefix = "application.jwt")
public class JwtService {

  private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

  private String secretKey;

  private int expiry;

  private String prefix;

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public void setExpiry(int expiry) {
    this.expiry = expiry;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public int getExpiry() {
    return expiry;
  }

  public String getPrefix() {
    return prefix;
  }

  public String generateJwt(Authentication auth) {
    return Jwts.builder()
               .setSubject(auth.getName())
               .setIssuedAt(new Date())
               .setExpiration(new Date(new Date().getTime() + expiry))
               .signWith(SignatureAlgorithm.HS512, secretKey)
               .compact();
  }

  public boolean isJwtValid(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (SignatureException e) {
      logger.error("JWT signature is invalid: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("JWT token is invalid: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }

  public String getUsernameFromJwt(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }
}
