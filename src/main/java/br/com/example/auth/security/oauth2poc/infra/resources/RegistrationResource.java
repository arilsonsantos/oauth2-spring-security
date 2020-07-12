package br.com.example.auth.security.oauth2poc.infra.resources;

import br.com.example.auth.security.oauth2poc.domain.User;
import br.com.example.auth.security.oauth2poc.domain.dto.UserDto;
import br.com.example.auth.security.oauth2poc.infra.resources.util.GenericResponse;
import br.com.example.auth.security.oauth2poc.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/public/registration/users")
public class RegistrationResource {

    private UserService userService;

    @PostMapping
    public ResponseEntity<Void> registerUser(@RequestBody UserDto userDto) {
        User user = userService.fromDto(userDto);
        userService.registerUser(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/confirmation")
    public ResponseEntity<GenericResponse> confirmRegistrationUser(@RequestParam("token") String token) {
        final Object result = this.userService.validateTokenVerification(token);
        if (result == null) {
            return ResponseEntity.ok().body(GenericResponse.builder().message("success").build());
        }
        return ResponseEntity.status(HttpStatus.SEE_OTHER).body(GenericResponse.builder().message(result.toString()).build());
    }

    @RequestMapping(method = RequestMethod.HEAD, path = "/resendConfirmationToken/{username}" )
    public ResponseEntity<Void> resendRegistrationToken(@PathVariable ("username") String username){
        this.userService.generateNewVerificationToken(username);
        return ResponseEntity.noContent().build();
    }

}
