package br.com.example.auth.security.oauth2poc.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "name")
@EqualsAndHashCode(of = "id")
@Document("ROLE")
public class Role {
    @Id
    private String id;
    private String name;

}
