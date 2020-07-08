package br.com.example.auth.security.oauth2poc.service;

import br.com.example.auth.security.oauth2poc.domain.TokenVerification;
import br.com.example.auth.security.oauth2poc.domain.User;
import br.com.example.auth.security.oauth2poc.domain.dto.UserDto;
import br.com.example.auth.security.oauth2poc.exceptions.ResourceAlreadyExistsException;
import br.com.example.auth.security.oauth2poc.exceptions.ResourceNotFoundException;
import br.com.example.auth.security.oauth2poc.infra.repository.RoleRepository;
import br.com.example.auth.security.oauth2poc.infra.repository.TokenVerificationRepository;
import br.com.example.auth.security.oauth2poc.infra.repository.UserRepository;
import br.com.example.auth.security.oauth2poc.service.email.AbstractEmailService;
import br.com.example.auth.security.oauth2poc.service.email.IEmailService;
import com.sun.el.parser.Token;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TokenVerificationRepository tokenVerificationRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IEmailService emailService;

    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    public User registerUser(User user){
       if(usernameExists(user.getUsername())){
           throw new ResourceAlreadyExistsException("User already exists");
       }
       user.setRoles(Arrays.asList(roleRepository.findByName("USER").get()));
       user.setEnable(false);
       user = create(user);
       this.emailService.sendConfirmationHtmlEmail(user, null );
       return user;
    }

    public void createVerificationTokenForUser(String token, User user){
        final TokenVerification tokenVerification = new TokenVerification(token, user);
        tokenVerificationRepository.save(tokenVerification);
    }

    private boolean usernameExists(String username){
       Optional<User> user = repository.findByUsername(username);
       if (user.isPresent()){
           return true;
       }
       return false;
    }

    public String validateTokenVerification(String token){
        final Optional<TokenVerification> tokenVerification = tokenVerificationRepository.findByToken(token);
        if (tokenVerification.isPresent()){
            final User user = tokenVerification.get().getUser();
            final Calendar cal = Calendar.getInstance();
            if ((tokenVerification.get().getExpireDate().getTime() - cal.getTime().getTime()) <= 0){
                return "Token expired";
            }
            user.setEnable(true);
            repository.save(user);
            return null;
        }
        return "Invalid token";
    }

    public User findByUsername(String username){
         return repository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("User not exist."));
    }

    public TokenVerification generateNewVerificationToken(String username) {
        User user = findByUsername(username);
        Optional<TokenVerification> token = tokenVerificationRepository.findByUser(user);
        token.get().updateToken(UUID.randomUUID().toString());
        TokenVerification updatedToken  = tokenVerificationRepository.save(token.get());
        emailService.sendConfirmationHtmlEmail(user, updatedToken);
        return updatedToken;

    }
}
