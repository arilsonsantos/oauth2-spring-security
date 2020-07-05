package br.com.example.auth.security.oauth2poc.infra.resources;

import br.com.example.auth.security.oauth2poc.domain.Role;
import br.com.example.auth.security.oauth2poc.domain.User;
import br.com.example.auth.security.oauth2poc.domain.dto.UserDto;
import br.com.example.auth.security.oauth2poc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        List<User> users = userService.findAll();
        List<UserDto> listDTO = users.stream().map(user -> new UserDto(user)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable String id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(new UserDto(user));
    }
    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        User user = userService.fromDto(userDto);
        return ResponseEntity.ok().body(new UserDto(userService.create(user)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable String id, @RequestBody UserDto userDto) {

        User user = userService.fromDto(userDto);
        user.setId(id);
        return ResponseEntity.ok().body(new UserDto(userService.update(user)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/roles")
    public ResponseEntity<List<Role>> findRoles(@PathVariable String id ){
        User user = userService.findById(id);

        return ResponseEntity.ok().body(user.getRoles());
    }
}
