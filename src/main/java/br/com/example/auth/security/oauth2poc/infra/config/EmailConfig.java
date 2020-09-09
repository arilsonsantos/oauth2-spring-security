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
        Properties props = mailSender.getJavaMailProperties();

        //Mailtraaap
        mailSender.setHost("smtp.mailtrap.io");
        mailSender.setPort(2525);
        mailSender.setUsername("4f967c13da91c3");
        mailSender.setPassword("762a7eb99def56");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        //Gmail
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//        mailSender.setUsername("aws.orion@gmail.com");
//        mailSender.setPassword("rzikfqzhagtytamk");
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");

        // Outlook
//        mailSender.setHost("smtp-mail.outlook.com");
//        mailSender.setPort(587);
//        mailSender.setUsername("orion.aws@outlook.com");
//        mailSender.setPassword("xiczunhurbgvxvuw");
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.tsl", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.ssl.trust", "smtp-mail.outlook.com");

        //AWS Simple Email Service

//        mailSender.setHost("email-smtp.us-east-1.amazonaws.com");
//        mailSender.setPort(587);
//        mailSender.setUsername("AKIAXZRCMYQHMH7MPWZT");
//        mailSender.setPassword("gkm1xzDblNJt3fsTe5ekWMs/l7hFs9KAvmIQUFu/");

        return mailSender;
    }
}
