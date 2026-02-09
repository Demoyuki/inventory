package com.gcu.inventory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.gcu.inventory.service.UserDetailsServiceImpl;

/**
 * Spring Security configuration class for the inventory application.
 * Configures dual authentication mechanisms: HTTP Basic Auth for REST API
 * and form-based login for web interface.
 * 
 * <p>This configuration implements two separate SecurityFilterChain beans
 * with explicit ordering to handle different authentication requirements:</p>
 * <ul>
 *   <li>REST API (/api/**) uses HTTP Basic Authentication</li>
 *   <li>Web UI uses form-based login with BCrypt password encoding</li>
 * </ul>
 * 
 * <p>Passwords are stored in MySQL database with BCrypt hashing for security.</p>
 * 
 * @author Victor Marrujo
 * @author Johnny Medina
 * @version 2.0
 * @since Milestone 6
 * @see UserDetailsServiceImpl
 * @see BCryptPasswordEncoder
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Password encoder bean.
     * 
     * Uses BCrypt to securely hash user passwords.
     * This encoder is used during registration and authentication.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Filter Chain 1: REST API Security
     * 
     * - Applies ONLY to URLs under /api/**
     * - Uses HTTP Basic authentication
     * - Stateless sessions (no HTTP session stored)
     * - CSRF protection disabled (appropriate for REST APIs)
     * 
     * This chain has higher priority and is evaluated first.
     */
    @Bean
    @Order(1) // IMPORTANT: Evaluated before the web UI filter chain
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {

        http
            // Apply this filter chain only to /api/** endpoints
            .securityMatcher("/api/**")

            // Disable CSRF for REST endpoints
            .csrf(csrf -> csrf.disable())

            // Do not create or use HTTP sessions
            .sessionManagement(sm ->
                sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Require authentication for all API requests
            .authorizeHttpRequests(auth ->
                auth.anyRequest().authenticated()
            )

            // Enable HTTP Basic authentication
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    /**
     * Filter Chain 2: Web UI Security
     * 
     * - Applies to all non-API requests
     * - Uses form-based login
     * - Allows public access to login and registration pages
     * - Protects all other pages
     * 
     * This chain is evaluated after the API filter chain.
     */
    @Bean
    @Order(2) // Evaluated second, after the API chain
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // Configure authorization rules
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", 
                    "/login", 
                    "/register", 
                    "/register/success"
                ).permitAll()
                .anyRequest().authenticated()
            )

            // Configure custom login page and behavior
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/products", true)
                .failureUrl("/login?error")
                .permitAll()
            )

            // Configure logout behavior
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }
}
