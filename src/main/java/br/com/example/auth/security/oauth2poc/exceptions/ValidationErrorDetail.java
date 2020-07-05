package br.com.example.auth.security.oauth2poc.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Getter
@Setter
public class ValidationErrorDetail {

    private int statusCode;

    private String message;

    private LocalDateTime timestamp;

    private Map<String, String> errors;
}