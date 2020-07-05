package br.com.example.auth.security.oauth2poc.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "name")
@EqualsAndHashCode(of = "id")
@Document("USER")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private boolean enable;

    @DBRef
    private List<Role> roles;

}
