package br.com.example.auth.security.oauth2poc.model.login;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import br.com.example.auth.security.oauth2poc.model.Role;
import br.com.example.auth.security.oauth2poc.model.User;

/**
 * ResourceOwnerMapper
 */
@Component
public class LoginUserMapper {

    public LoginUser map(User user){
        List<GrantedAuthority> roles = user.getRoles().stream()
        .map(Role::getName)
        .map(s -> "ROLE_" + s)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

        return new LoginUser(user.getUsername(), user.getPassword(), roles, user.isEnable());

    }
}
