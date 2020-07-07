package br.com.example.auth.security.oauth2poc.infra.repository;

import br.com.example.auth.security.oauth2poc.domain.TokenVerification;
import br.com.example.auth.security.oauth2poc.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenVerificationRepository extends MongoRepository<TokenVerification, String> {

    Optional<TokenVerification> findByToken(String token);

    Optional<TokenVerification> findByUser(User user);

}
