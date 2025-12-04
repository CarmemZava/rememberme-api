package com.zavattieri.RememberMe.security.jwt;

import com.zavattieri.RememberMe.repository.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component //this class will be managed by Spring Security , it is similar to @Bean (methods) but used for classes
public class JwtAuthFilter extends OncePerRequestFilter { //extends OncePerRequestFilter to ensure it is executed once per request HTTP

    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { //implement custom filtering logic for JWT authentication
        try {
            var token = recoverToken(request); //extract the JWT token from the HTTP request using recoverToken method

            if(token != null){
                var email = tokenService.validateToken(token); //validate the token using tokenService

                if(email != null){
                    UserDetails user = userRepository.findByEmail(email); //retrieve the user details from the database using UserRepository

                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); //create an authentication object using the user details and their authorities (roles/permissions)
                    SecurityContextHolder.getContext().setAuthentication(authentication); //set the authentication object in the SecurityContextHolder, this way I can get the user context anywhere in the application
                }
            }
        }
        catch (Exception e){
            System.out.println("Error validating token: " + e.getMessage());
        }

        filterChain.doFilter(request, response); //continues the filter chain, allowing the request to proceed to the next filter or resource

    }

    private String recoverToken(HttpServletRequest request){ //method to extract JWT token, String from "Authorization: Bearer jdhfwiueJj.."
        var authHeader = request.getHeader("Authorization"); //get the value of the Authorization header from the HTTP request
        if(authHeader == null) return null; //if the header is not present, return null
        return authHeader.replace("Bearer ", ""); //remove the "Bearer " prefix
    }
}
