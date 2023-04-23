package br.com.example.auth.security.oauth2poc.resources;

import br.com.example.auth.security.oauth2poc.model.Customer;
import br.com.example.auth.security.oauth2poc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerResource {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> searchAll() {
        return ResponseEntity.ok(this.customerService.findAll());
    }

}
