package br.com.example.auth.security.oauth2poc.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = "id")
@Document("TOKEN_VERIFICATION")
@NoArgsConstructor
public class TokenVerification {

    private static final int EXPIRATION = 60 * 24;

    @Id
    private String id;
    private String token;
    private LocalDateTime expireDate;

    @DBRef(lazy = true)
    private User user;

    public TokenVerification(String token){
        this.token = token;
        this.expireDate = calculateExpireDate(EXPIRATION);
    }

    public TokenVerification(final String token, final User user){
        this.token = token;
        this.user = user;
        this.expireDate = calculateExpireDate(EXPIRATION);
    }

    private LocalDateTime calculateExpireDate(final int expireTimeInMinutes){
        return LocalDateTime.now().plusMinutes(expireTimeInMinutes);
    }

    public void updateToken(final String token){
        this.token = token;
        this.expireDate = calculateExpireDate(EXPIRATION);
    }

}
