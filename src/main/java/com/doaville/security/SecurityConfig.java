package com.doaville.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")) // Exemplo para liberar o H2 em dev
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/itens-doacao/**").authenticated()
                        .anyRequest().permitAll()
                )
                .httpBasic(httpBasic -> {}) // ativa basic authentication (não deprecated)
                .formLogin(formLogin -> formLogin.disable()); // desativa form login (não deprecated)

        return http.build();
    }
}
