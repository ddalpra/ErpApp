package it.ddalpra.acme.erpApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1. Proteggi tutte le rotte, eccetto /
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/webjars/**").permitAll() // Home page e risorse statiche sono pubbliche
                .anyRequest().authenticated() // Tutte le altre richiedono l'autenticazione
            )
            // 2. Abilita il login OAuth2/OIDC
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/oauth2/authorization/keycloak") // Reindirizza al provider Keycloak
            )
            // 3. Abilita il logout
            .logout(logout -> logout
                .logoutSuccessUrl("/") // Dopo il logout reindirizza alla home
                .permitAll()
            );

        return http.build();
    }
}