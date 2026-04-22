package com.zavattieri.RememberMe.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice //annotation that allows to handle exceptions globally across the whole application, it is a specialization of @ControllerAdvice and @ResponseBody, meaning that the methods in this class will return
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class) //@ExceptionHandler annotation is used to specify the type of exception that the method will handle, in this case, it will handle MethodArgumentNotValidExc.
                                                             //MethodArgumentNotValid...class is the class of the exception that will be handled, which is thrown when validation on an argument annotated with @Valid fails
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex){
    Map<String, String> errors = new HashMap<>();           //Create a Map<Key,Value> to store the error messages, where the key is the field name and the value is the error message
        ex.getBindingResult().getFieldErrors().forEach(    //getBindingResult() and getFieldErrors() are methods provided by the MethodArgumentNotValidException class, and return the result of the validation process (BindingResult) and the list of field errors, respectively.
                error ->  errors.put(            //The forEach method is used to iterate over the list of field errors and populate the errors map with the field name and the corresponding error message
                        error.getField(),
                        error.getDefaultMessage()));
                return ResponseEntity.badRequest().body(errors);  //return a bad request response (400) with the errors map as the response body, allowing the client to understand what went wrong with the validation
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Map<String,String>> handleDuplicateEmail(DuplicateEmailException ex){
        return ResponseEntity.badRequest().body(Map.of("email", ex.getMessage())); //return a bad request response (400) with a map containing the key "email" and the value being the message from the exception, indicating that the email is already in use
    }

    @ExceptionHandler(BadCredentialsException.class) //BadCredentialsException is a Spring Security exception that is thrown when authentication fails due to invalid credentials, it included in the authenticationManager.authenticate() method in the AuthenticationService class, so when the user provides an incorrect email or password, this exception will be thrown and handled by this method
    public ResponseEntity<Map<String, String>> handleBadCredentials(BadCredentialsException ex){
        return ResponseEntity.status(401).body(Map.of("error", "Invalid email or password")); //return a bad request response (400) with a map containing the key "error" and a generic message indicating that the email or password is invalid, this is used to avoid giving too much information to potential attackers about which part of the credentials is incorrect))
    }
}
