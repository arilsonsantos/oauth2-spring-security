package br.com.example.auth.security.oauth2poc.domain;

import ch.qos.logback.core.net.SyslogOutputStream;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

@Data
@EqualsAndHashCode(of = "id")
@Document("TOKEN_VERIFICATION")
@NoArgsConstructor
public class TokenVerification {

    private static final int EXPIRATION = 60 * 24;

    @Id
    private String id;
    private String token;
    private Date expireDate;

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

    private Date calculateExpireDate(final int expireTimeInMinutes){
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expireTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void updateToken(final String token){
        this.token = token;
        this.expireDate = calculateExpireDate(EXPIRATION);
    }

}
