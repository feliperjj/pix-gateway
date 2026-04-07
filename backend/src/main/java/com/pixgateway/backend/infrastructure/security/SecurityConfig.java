package com.pixgateway.backend.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {}) // Ativa o suporte a CORS
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Liberação total para teste
            );
        
        return http.build();
    }

    // ESSE BEAN É O QUE RESOLVE O PRE-FLIGHT (OPTIONS) DEFINITIVAMENTE
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.addAllowedOrigin("*"); // Libera qualquer site
        config.addAllowedHeader("*"); // Libera qualquer header
        config.addAllowedMethod("*"); // Libera GET, POST, OPTIONS, etc.
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}