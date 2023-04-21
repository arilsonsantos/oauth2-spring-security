package br.com.example.auth.security.oauth2poc.service;

import br.com.example.auth.security.oauth2poc.model.Customer;
import br.com.example.auth.security.oauth2poc.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends AbstractService<Customer, String, CustomerRepository> {

    public CustomerService(CustomerRepository repository) {
        super(repository);
    }

}
