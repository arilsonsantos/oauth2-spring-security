package br.com.example.auth.security.oauth2poc.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ErrorDetail {

    private int statusCode;

    private String message;

    private String path;

    
}