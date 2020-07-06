package br.com.example.auth.security.oauth2poc.domain.dto;

import br.com.example.auth.security.oauth2poc.domain.Role;
import br.com.example.auth.security.oauth2poc.domain.User;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String id;
    private String username;
    private String password;
    private boolean enable;
    private List<Role> roles = new ArrayList<>();

    public UserDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.enable = user.isEnable();
        this.roles = user.getRoles();
    }

}
