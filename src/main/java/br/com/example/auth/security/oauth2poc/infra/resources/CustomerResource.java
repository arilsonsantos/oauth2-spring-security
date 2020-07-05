package br.com.example.auth.security.oauth2poc.infra.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.example.auth.security.oauth2poc.domain.Customer;
import br.com.example.auth.security.oauth2poc.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerResource {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> searchAll() {
        return ResponseEntity.ok(this.customerService.findAll());
    }
    
}
