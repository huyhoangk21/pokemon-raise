package com.hoangle.pokemon.config;

import com.hoangle.pokemon.security.AuthEntryPointExceptionHandler;
import com.hoangle.pokemon.security.JwtTokenFilter;
import com.hoangle.pokemon.service.JwtService;
import com.hoangle.pokemon.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final SecurityUserService securityUserService;
  private final JwtService jwtService;
  private final AuthEntryPointExceptionHandler authEntryPointExceptionHandler;

  @Autowired
  public SecurityConfig(SecurityUserService securityUserService,
                        JwtService jwtService,
                        AuthEntryPointExceptionHandler authEntryPointExceptionHandler) {
    this.securityUserService = securityUserService;
    this.jwtService = jwtService;
    this.authEntryPointExceptionHandler = authEntryPointExceptionHandler;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/v1/api/auth/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .exceptionHandling().authenticationEntryPoint(authEntryPointExceptionHandler)
        .and()
        .addFilterBefore(new JwtTokenFilter(securityUserService, jwtService), UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(securityUserService).passwordEncoder(passwordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }
}
