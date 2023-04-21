package br.com.example.auth.security.oauth2poc.model.dto;

import br.com.example.auth.security.oauth2poc.model.Role;
import br.com.example.auth.security.oauth2poc.model.User;
import lombok.*;

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
    private String email;
    private List<Role> roles = new ArrayList<>();

    public UserDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.enable = user.isEnable();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }

}
