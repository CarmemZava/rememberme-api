package com.zavattieri.RememberMe.controller.auth;

import com.zavattieri.RememberMe.dto.auth.LoginRequestDTO;
import com.zavattieri.RememberMe.dto.auth.LoginResponseDTO;
import com.zavattieri.RememberMe.dto.auth.RegisterRequestDTO;
import com.zavattieri.RememberMe.dto.auth.RegisterResponseDTO;
import com.zavattieri.RememberMe.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //indicates that this class is a RESTful controller, meaning it will handle HTTP requests and return data in JSON or XML format
@RequestMapping("/auth") //path prefix for all endpoints defined in this controller


public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) { //it means that the parameter is and object LoginRequest that will be populated with the data from the request body, and it should be validated according to the constraints defined in the LoginRequest class

        LoginResponseDTO response = authenticationService.login(data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid RegisterRequestDTO data) {

        RegisterResponseDTO response = authenticationService.register(data); //call the register method of the AuthenticationService to handle the registration logic, which includes checking for duplicate emails, encrypting the password, and saving the new user to the database
        return ResponseEntity.ok(response); //return HTTP 200 OK response indicating successful registration
    }


}
