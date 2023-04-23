package br.com.example.auth.security.oauth2poc.config;


import br.com.example.auth.security.oauth2poc.model.Customer;
import br.com.example.auth.security.oauth2poc.model.Role;
import br.com.example.auth.security.oauth2poc.model.User;
import br.com.example.auth.security.oauth2poc.repository.CustomerRepository;
import br.com.example.auth.security.oauth2poc.repository.RoleRepository;
import br.com.example.auth.security.oauth2poc.repository.TokenVerificationRepository;
import br.com.example.auth.security.oauth2poc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class DataLoaderConfig implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CustomerRepository customerRepository;
    private final TokenVerificationRepository tokenVerificationRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        roleRepository.deleteAll();
        userRepository.deleteAll();
        customerRepository.deleteAll();
        tokenVerificationRepository.deleteAll();

        Customer c1 = Customer.builder().nome("Customer AAA").build();
        Customer c2 = Customer.builder().nome("Customer BBB").build();
        createCustomerIfNotFound(c1);
        createCustomerIfNotFound(c2);

        Role admin = Role.builder().name("ADMIN").build();
        Role user = Role.builder().name("USER").build();
        createRoleIfNotFound(admin);
        createRoleIfNotFound(user);

        List<Role> joao_role = Arrays.asList(admin);
        List<Role> maria_role = Arrays.asList(admin, user);
        List<Role> jose_role = Arrays.asList(user);

        var pass = passwordEncoder.encode("123");
        User user1 = User.builder().username("Joao").username("joao").roles(joao_role).password(pass).email("arilsonsantos@gmail.com").enable(true).build();
        User user2 = User.builder().username("Maria").username("maria").roles(maria_role).password(pass).email("arilsonsantos@gmail.com").enable(true).build();
        User user3 = User.builder().username("Jose").username("jose").roles(jose_role).password(pass).email("arilsonsantos@gmail.com").enable(true).build();

        createUserIfNotFound(user1);
        createUserIfNotFound(user2);
        createUserIfNotFound(user3);
    }

    private void createUserIfNotFound(final User user) {
        Optional<User> obj = userRepository.findByUsername(user.getUsername());
        if (obj.isPresent()) {
            return;
        }
        userRepository.save(user);
    }

    private void createRoleIfNotFound(final Role role) {
        Optional<Role> obj = roleRepository.findByName(role.getName());
        if (obj.isPresent()) {
            return;
        }
        roleRepository.save(role);
    }

    private void createCustomerIfNotFound(final Customer customer) {
        Optional<Role> obj = roleRepository.findByName(customer.getNome());
        if (obj.isPresent()) {
            return;
        }
        customerRepository.save(customer);
    }

}
