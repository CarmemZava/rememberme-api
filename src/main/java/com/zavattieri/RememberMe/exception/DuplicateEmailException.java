package com.zavattieri.RememberMe.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);     //message is passed to the superclass constructor, it comes from the throw
    }
}
