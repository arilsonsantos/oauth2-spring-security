package br.com.example.auth.security.oauth2poc.infra.repository;

import br.com.example.auth.security.oauth2poc.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

    Optional<Role> findByName(String name);
}
