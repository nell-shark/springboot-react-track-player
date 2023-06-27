package com.nellshark.musicplayer.configs;

import com.nellshark.musicplayer.services.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CsrfTokenRequestHandler csrfTokenRequestHandler;
    private final OAuth2UserService oauth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //    @formatter:off
        return http
                .cors()
                    .and()
                .csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .csrfTokenRequestHandler(csrfTokenRequestHandler)
                    .and()
                .authorizeHttpRequests()
                    .anyRequest()
                    .permitAll()
                    .and()
                .oauth2Login()
                    .userInfoEndpoint()
                    .userService(oauth2UserService)
                    .and()
                    .defaultSuccessUrl("http://localhost:3000")
                    .and()
                .logout()
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                    .logoutSuccessUrl("http://localhost:3000")
                    .and()
                .build();
        //    @formatter:on
    }
}