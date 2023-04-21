package br.com.example.auth.security.oauth2poc.service.email;

import br.com.example.auth.security.oauth2poc.model.TokenVerification;
import br.com.example.auth.security.oauth2poc.model.User;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public interface IEmailService {

    void sendHtmlEmail(MimeMessage message) throws MessagingException;
    void sendConfirmationHtmlEmail(User user, TokenVerification tokenVerification, boolean emailConfirmation);

}
