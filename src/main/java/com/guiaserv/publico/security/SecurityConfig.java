package com.guiaserv.publico.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        // AUTH
                        .requestMatchers(HttpMethod.POST, "/api/auth/cadastro").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/auth/me").hasAnyRole("USER", "ADMIN")

                        // SWAGGER
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // SERVIÇOS - PÚBLICO
                        .requestMatchers(HttpMethod.GET, "/api/servicos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/servicos/**").permitAll()

                        // UNIDADES - PÚBLICO
                        .requestMatchers(HttpMethod.GET, "/api/unidades").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/unidades/**").permitAll()

                        // DOCUMENTOS - USER OU ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/servicos/*/documentos").hasAnyRole("USER", "ADMIN")

                        // AVALIAÇÕES - USER OU ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/avaliacoes").hasAnyRole("USER", "ADMIN")

                        // ADMIN - SERVIÇOS
                        .requestMatchers(HttpMethod.POST, "/api/servicos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/servicos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/servicos/**").hasRole("ADMIN")

                        // ADMIN - UNIDADES
                        .requestMatchers(HttpMethod.POST, "/api/unidades").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/unidades/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/unidades/**").hasRole("ADMIN")

                        // ADMIN - DOCUMENTOS
                        .requestMatchers(HttpMethod.POST, "/api/documentos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/documentos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/documentos/**").hasRole("ADMIN")

                        // CATEGORIAS - PÚBLICO
                        .requestMatchers(HttpMethod.GET, "/api/categorias").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categorias/**").permitAll()

                        // ADMIN - CATEGORIAS
                        .requestMatchers(HttpMethod.POST, "/api/categorias").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/categorias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/categorias/**").hasRole("ADMIN")

                        // SERVIÇOS - PÚBLICO
                        .requestMatchers(HttpMethod.GET, "/api/servicos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/servicos/**").permitAll()

                        // ADMIN - SERVIÇOS
                        .requestMatchers(HttpMethod.POST, "/api/servicos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/servicos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/servicos/**").hasRole("ADMIN")

                            // UNIDADES - PÚBLICO
                        .requestMatchers(HttpMethod.GET, "/api/unidades").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/unidades/**").permitAll()

                        // ADMIN - UNIDADES
                        .requestMatchers(HttpMethod.POST, "/api/unidades").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/unidades/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/unidades/**").hasRole("ADMIN")

                        // DOCUMENTOS - USER OU ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/servicos/*/documentos").hasAnyRole("USER", "ADMIN")

                        // ADMIN - DOCUMENTOS
                        .requestMatchers(HttpMethod.POST, "/api/documentos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/documentos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/documentos/**").hasRole("ADMIN")

                        // QUALQUER OUTRA ROTA EXIGE LOGIN
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}