package com.zavattieri.RememberMe.security.config;

import com.zavattieri.RememberMe.security.jwt.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration //indicates that this class contains configuration settings for the application
@EnableWebSecurity //enables Spring Security's web security support and lets you customize web security accordingly to this class

public class SecurityConfiguration {
    @Autowired
    JwtAuthFilter jwtAuthFilter;
    @Bean //indicates that this method produces a bean to be managed, it is associated with the @Configuration
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception { //SecurityFilterChain bean that defines the security filter chain for HTTP requests, it has conditions, rules, and permissions for incoming requests
                                                                                //HttpSecurity parameter is used to configure web based security for specific http requests
        return httpSecurity
                .cors(cors -> {}) //activates CORS (corsConfigurationSource())
                .csrf(csrf->csrf.disable()) //disables CSRF protection, which is often disabled for stateless APIs that do not use cookies for authentication
//                .cors(cors -> cors.disable())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //configures the state for Stateless session management, meaning the server will not store any session information about the client

                .authorizeHttpRequests(authorize->authorize //authorizeHttpRequests() is used to define authorization rules for incoming HTTP requests
                        .requestMatchers("/auth/**").permitAll() //allows unrestricted access to any endpoint that starts with /auth/
                        .anyRequest().authenticated()) //requires authentication for any other request

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) //adds the custom JwtAuthFilter before the UsernamePasswordAuthenticationFilter in the security filter chain, so that JWT token validation occurs before the standard authentication process

                .build(); //builds the SecurityFilterChain object with the defined configurations
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception { //returns the AuthenticationManager bean that is responsible for processing authentication requests to be used on AuthenticationController
     return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){ //method used by AuthenticationManager to encode and verify passwords
        return new BCryptPasswordEncoder(); //PasswordEncoder bean that uses BCrypt hashing algorithm to securely hash passwords
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){ //method dictates CORS configuration to allow access to Angular
        CorsConfiguration cors = new CorsConfiguration();

        cors.setAllowedOrigins(List.of("http://localhost:4200")); //permit requisitions of this specific origin
        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); //permit this HTTP methods, includes "OPTIONS" which is preflight CORS - browser sends this special requisition before GET, POST...
        cors.setAllowedHeaders(List.of("*")); //permits all type of headers such Authorization: Bearer token
        cors.setAllowCredentials(true); //permits credential sendings suchs as cookies, headers authorization and others

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); //New instance with CORS rules

        source.registerCorsConfiguration("/**", cors); //applied the source cors rules to all end points of API

        return source;

    }

}
