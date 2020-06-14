package br.com.example.auth.security.oauth2poc.service;

import org.springframework.stereotype.Service;

import br.com.example.auth.security.oauth2poc.domain.Customer;
import br.com.example.auth.security.oauth2poc.infra.repository.CustomerRepository;

@Service
public class CustomerService extends AbstractService<Customer, Integer, CustomerRepository> {

    public CustomerService(CustomerRepository repository) {
        super(repository);
    }

}
