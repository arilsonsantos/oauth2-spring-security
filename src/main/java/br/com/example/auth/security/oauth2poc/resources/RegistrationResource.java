package br.com.example.auth.security.oauth2poc.resources;

import br.com.example.auth.security.oauth2poc.model.TokenVerification;
import br.com.example.auth.security.oauth2poc.model.User;
import br.com.example.auth.security.oauth2poc.model.dto.UserDto;
import br.com.example.auth.security.oauth2poc.resources.util.GenericResponse;
import br.com.example.auth.security.oauth2poc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/registration/users")
public class RegistrationResource {

    private final UserService userService;

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

    @GetMapping(path = "/resendConfirmationToken/{username}" )
    public ResponseEntity<Void> resendRegistrationToken(@PathVariable ("username") String username){
        this.userService.generateNewVerificationToken(username, false);
        return ResponseEntity.noContent().build();
    }

    //TODO change to reset user
    @PostMapping("/resetPassword/users")
    public ResponseEntity<Void> resetPassword (@RequestParam("username") String username){
        this.userService.generateNewVerificationToken(username, true);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/changePassword")
    public ResponseEntity<GenericResponse> resetPassword (@RequestParam("id") String idUser, @RequestParam("token") String token){
        final String result = userService.validatePasswordResetToken(idUser, token);
        if (result == null){
            return ResponseEntity.ok().body(GenericResponse.builder().message("success").build());
        }
        return  ResponseEntity.status(HttpStatus.SEE_OTHER).body(GenericResponse.builder().message(result.toString()).build());
    }

    @GetMapping("/savePassword")
    public ResponseEntity<GenericResponse> savePassword (@RequestParam("token") String token, @RequestParam("password") String password){
        final String result = userService.validateTokenVerification(token);
        if (result != null){
            return ResponseEntity.ok().body(GenericResponse.builder().message(result.toString()).build());
        }
        final TokenVerification tokenVerification = userService.getVerificationTokenByToken(token);
        if (tokenVerification != null){
            userService.changeUserPassword(tokenVerification.getUser(), password);
        }

        return ResponseEntity.noContent().build();
    }




}
