package br.com.example.auth.security.oauth2poc.login;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import br.com.example.auth.security.oauth2poc.domain.Role;
import br.com.example.auth.security.oauth2poc.domain.User;

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