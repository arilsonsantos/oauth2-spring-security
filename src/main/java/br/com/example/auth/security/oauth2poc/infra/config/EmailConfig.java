package br.com.example.auth.security.oauth2poc.infra.config;

import br.com.example.auth.security.oauth2poc.service.email.IEmailService;
import br.com.example.auth.security.oauth2poc.service.email.SmtpEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:application.yml")
public class EmailConfig {

    @Bean
    public IEmailService emailService(){
        return new SmtpEmailService();
    }

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("orion.as.service@gmail.com");
        mailSender.setPassword("cjxsncdvdcmxpbis");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
