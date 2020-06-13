package br.com.example.auth.security.oauth2poc.infra.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeResource {

    @GetMapping
    public ResponseEntity<String> homeText() {
        return ResponseEntity.ok("Its ok, you do not need authentication here");
    }

}
