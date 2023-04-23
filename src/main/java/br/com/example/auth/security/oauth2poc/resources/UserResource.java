package br.com.example.auth.security.oauth2poc.resources;

import br.com.example.auth.security.oauth2poc.model.Role;
import br.com.example.auth.security.oauth2poc.model.User;
import br.com.example.auth.security.oauth2poc.model.dto.UserDto;
import br.com.example.auth.security.oauth2poc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

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

    @GetMapping("/main")
    public ResponseEntity<UserDto> getMainUser(Principal principal){
        User user = this.userService.findByUsername(principal.getName());
        UserDto userDto = userService.toDto(user);
        userDto.setPassword(null);
        return ResponseEntity.ok().body(userDto);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        TokenStore tokenStore = new InMemoryTokenStore();
        String authHeader = request.getHeader("Authorization");

        if (authHeader!= null){
            String token = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenServices.readAccessToken(token);
            tokenStore.removeAccessToken(accessToken);
            tokenServices.revokeToken(String.valueOf(accessToken));
        }
        return ResponseEntity.noContent().build();
    }

}
