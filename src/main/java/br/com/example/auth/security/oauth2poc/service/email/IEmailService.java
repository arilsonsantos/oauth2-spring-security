package br.com.example.auth.security.oauth2poc.service.email;

import br.com.example.auth.security.oauth2poc.domain.TokenVerification;
import br.com.example.auth.security.oauth2poc.domain.User;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public interface IEmailService {

    void sendHtmlEmail(MimeMessage message);
    void sendConfirmationHtmlEmail(User user, TokenVerification tokenVerification);

}
