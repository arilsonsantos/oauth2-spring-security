package br.com.example.auth.security.oauth2poc.repository;

import br.com.example.auth.security.oauth2poc.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

}
