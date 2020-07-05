package br.com.example.auth.security.oauth2poc.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceAlreadyExistsException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
    
}