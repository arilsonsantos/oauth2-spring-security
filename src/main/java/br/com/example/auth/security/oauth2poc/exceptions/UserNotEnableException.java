package br.com.example.auth.security.oauth2poc.exceptions;

public class UserNotEnableException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserNotEnableException(String message) {
        super(message);
    }
    
}