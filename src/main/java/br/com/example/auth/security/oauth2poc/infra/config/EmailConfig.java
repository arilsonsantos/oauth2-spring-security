package br.com.example.auth.security.oauth2poc.infra.config;

import br.com.example.auth.security.oauth2poc.service.email.IEmailService;
import br.com.example.auth.security.oauth2poc.service.email.SmtpEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:application.yml")
public class EmailConfig {

    @Bean
    public IEmailService emailService(){
        return new SmtpEmailService();
    }

    @Bean
    public JavaMailSender javaMailSender(){
        return new JavaMailSenderImpl();
    }
}
