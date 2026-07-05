package it.uniroma3.siw.tornei.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * JdbcUserDetailsManager con query personalizzate sulla tabella "utente"
     * (adattamento del "credentials"/"role" delle slide alla nostra tabella
     * "utente"/"ruolo").
     *
     * usersByUsernameQuery: deve restituire (username, password, enabled)
     * authoritiesByUsernameQuery: deve restituire (username, authority)
     *   -> qui "authority" e' direttamente il valore di ruolo (es. "ADMIN", "USER"),
     *      per questo in SecurityFilterChain usiamo hasAnyAuthority("ADMIN")
     *      e NON hasRole("ADMIN") (che si aspetterebbe il prefisso ROLE_).
     */
    @Bean
    public UserDetailsService userDetailsService() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        manager.setUsersByUsernameQuery(
            "select username, password, true as enabled from utente where username = ?"
        );

        manager.setAuthoritiesByUsernameQuery(
            "select username, ruolo as authority from utente where username = ?"
        );

        return manager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // pagine pubbliche (Sezione 4.1 della traccia)
                .requestMatchers(
                    "/", "/login",
                    "/tornei", "/tornei/**",
                    "/squadre", "/squadre/**",
                    "/partite", "/partite/**",
                    "/css/**", "/js/**", "/images/**"
                ).permitAll()
                // funzionalita' amministrative (Sezione 4.3)
                .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                // tutto il resto richiede login (es. commenti - Sezione 4.2)
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }
}