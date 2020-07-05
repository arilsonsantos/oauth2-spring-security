package br.com.example.auth.security.oauth2poc.infra.repository;

import br.com.example.auth.security.oauth2poc.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

}
