package br.com.example.auth.security.oauth2poc.service;

import br.com.example.auth.security.oauth2poc.domain.User;
import br.com.example.auth.security.oauth2poc.domain.dto.UserDto;
import br.com.example.auth.security.oauth2poc.exceptions.ResourceNotFoundException;
import br.com.example.auth.security.oauth2poc.infra.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository repository;

    public User create(User user) {
        return repository.save(user);
    }

    public User update(User user){
        User userToUpdate = findByUser(user);

        userToUpdate = User.builder()
                .id(userToUpdate.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .enable(user.isEnable())
                .build();

        return  create(userToUpdate);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(String id) {
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException("Resource not found."));
    }

    public User findByUser(User user) {
        Optional<User> userFound = repository.findById(user.getId());
        return userFound.orElseThrow(() -> new ResourceNotFoundException("Resource not found."));
    }

    public User fromDto(UserDto dto){
        ModelMapper mapper = new ModelMapper();
        User user = mapper.map(dto, User.class);
        return user;
    }

    public UserDto toDto(User user){
        ModelMapper mapper = new ModelMapper();
        UserDto userDto = mapper.map(user, UserDto.class);
        return userDto;
    }

    public void deleteById(String id) {
        User userToDelete = findById(id);
        repository.delete(userToDelete);
    }
}
