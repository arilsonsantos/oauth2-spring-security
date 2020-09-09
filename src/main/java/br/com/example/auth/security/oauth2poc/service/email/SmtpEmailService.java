package br.com.example.auth.security.oauth2poc.service.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

@Slf4j
public class SmtpEmailService extends AbstractEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public SmtpEmailService() {
    }

    @Override
    public void sendHtmlEmail(MimeMessage message) {
        log.info("Sending email...");
        javaMailSender.send(message);
        log.info("Email has sent with success.");

    }
}
