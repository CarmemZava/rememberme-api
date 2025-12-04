package com.zavattieri.RememberMe.controller.auth;

import com.zavattieri.RememberMe.domain.user.User;
import com.zavattieri.RememberMe.dto.auth.LoginResponseDTO;
import com.zavattieri.RememberMe.dto.auth.LoginRequestDTO;
import com.zavattieri.RememberMe.dto.auth.RegisterRequestDTO;
import com.zavattieri.RememberMe.dto.auth.RegisterResponseDTO;
import com.zavattieri.RememberMe.repository.user.UserRepository;
import com.zavattieri.RememberMe.security.jwt.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //indicates that this class is a RESTful controller, meaning it will handle HTTP requests and return data in JSON or XML format
@RequestMapping("/auth") //path prefix for all endpoints defined in this controller


public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) { //it means that the parameter is and object LoginRequest that will be populated with the data from the request body, and it should be validated according to the constraints defined in the LoginRequest class

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password()); //UsernamePasswordAuthenticationToken is a class provided by Spring Security that represents an authentication request using the username and password that comes from the DTO data
        var auth = this.authenticationManager.authenticate(usernamePassword); //authenticationManager is a Spring Security component responsible for processing authentication requests, it takes the usernamePassword token and verifies the credentials against the user details service and other configured authentication providers

        User user = (User) auth.getPrincipal(); //getPrincipal() method retrieves the authenticated user details from the Authentication object returned by the authenticationManager
                                                //we cast it to our User class to access user-specific information

        var token = tokenService.generateToken(user);

        var response = new LoginResponseDTO(token, user.getName(), user.getEmail(), user.getRole().name()); //create a new AuthResponseDTO object containing the generated JWT token and user details (name, email

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid RegisterRequestDTO data) {

        if(this.userRepository.findByEmail(data.email()) != null){  //search for existing user with the same email using UserRepository method findByEmail
            return ResponseEntity.badRequest().build();             //if user already exists, return HTTP 400 Bad Request response
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password()); //encrypt the password using BCrypt hashing algorithm

        User newUser = new User(data.name(), data.email(), encryptedPassword, data.role());  //create a new User object with the provided name, email, encrypted password, and role from the RegisterRequestDTO
        this.userRepository.save(newUser); //save the new user to the database using UserRepository method save

        var response = new RegisterResponseDTO("User registered sucessfuly", newUser.getEmail());

        return ResponseEntity.ok(response); //return HTTP 200 OK response indicating successful registration
    }


}
