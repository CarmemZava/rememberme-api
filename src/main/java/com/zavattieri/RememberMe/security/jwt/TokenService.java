package com.zavattieri.RememberMe.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zavattieri.RememberMe.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service

public class TokenService {

    @Value("${api.security.jwt.secret}") //inject the secret key from application properties -> environment variable defined in application.properties
    private String secret; //secret key to sign the JWT token

    public String generateToken(User user){ //method to generate JWT token for a given user
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret); //use HMAC256 algorithm with a secret key -> should be stored in application properties
            String token = JWT.create() // to create a new JWT token with:
                    .withIssuer("RememberMe API") //issuer of the token -> the application name
                    .withSubject(user.getEmail()) //subject of the token -> the user's email
                    .withExpiresAt(genExpirationDate()) //expiration date of the token -> method to be implemented
                    .sign(algorithm); //sign the token with the algorithm
            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error generating token JWT", exception);
        }
    }

    public Instant genExpirationDate(){ //method to generate the expiration date of the token
        return Instant.now().plusSeconds(86400); //token valid for 24 hours (86400 seconds)
    }

    public String validateToken(String token){ //method to validate the token and return the subject (user's email)
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret); //use HMAC256 algorithm with the secret key to verify the token
            return JWT.require(algorithm)//require the algorithm to verify the token with:
                    .withIssuer("RememberMe API")//has to be the same issuer as when the token was created
                    .build() //build the verifier
                    .verify(token) //verify the token
                    .getSubject(); //return the subject (user's email)
        } catch (JWTVerificationException exception){
            System.out.println("Token invalid: " + exception.getMessage());
            return null;
        }


    }

}
