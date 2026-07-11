package it.uniroma3.siw.tornei.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/*
 * Configurazione CORS (Cross-Origin Resource Sharing).
 *
 * Il frontend React gira su http://localhost:5173 (server di sviluppo
 * Vite), il backend Spring Boot su http://localhost:8080: sono due
 * "origini" diverse agli occhi del browser (Same-Origin Policy), quindi
 * senza questa configurazione tutte le chiamate Axios verrebbero
 * bloccate dal browser stesso, PRIMA ancora di arrivare al server.
 *
 * Questo bean autorizza esplicitamente le richieste che arrivano da
 * localhost:5173 verso i path "/api/**".
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}