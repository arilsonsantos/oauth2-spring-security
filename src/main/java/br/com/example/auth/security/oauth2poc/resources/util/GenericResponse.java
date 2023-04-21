package br.com.example.auth.security.oauth2poc.resources.util;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenericResponse {

    private String message;
    private String error;

}
