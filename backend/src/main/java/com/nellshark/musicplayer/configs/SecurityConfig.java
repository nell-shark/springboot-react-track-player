package com.nellshark.musicplayer.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf().disable() // Delete
            .authorizeHttpRequests(
                    authorization -> authorization.requestMatchers("/**").permitAll())
            .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl("http://localhost:3000/tracks"));
    return http.build();
  }
}
