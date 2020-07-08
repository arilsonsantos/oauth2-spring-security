package br.com.example.auth.security.oauth2poc.service;

import br.com.example.auth.security.oauth2poc.domain.User;
import br.com.example.auth.security.oauth2poc.exceptions.UserNotEnableException;
import br.com.example.auth.security.oauth2poc.login.LoginUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.example.auth.security.oauth2poc.infra.repository.UserRepository;
import br.com.example.auth.security.oauth2poc.login.LoginUserMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService  {
    
    private UserRepository userRepository;
    private LoginUserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username)  {
        LoginUser user = this.userRepository.findByUsername(username)
            .map(mapper::map)
            .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        if (!user.isEnabled()){
            throw new UserNotEnableException(username);
        }

        return user;
    }

}
