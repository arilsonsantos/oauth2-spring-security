package br.com.example.auth.security.oauth2poc.service.email;

import br.com.example.auth.security.oauth2poc.domain.TokenVerification;
import br.com.example.auth.security.oauth2poc.domain.User;
import br.com.example.auth.security.oauth2poc.exceptions.ResourceNotFoundException;
import br.com.example.auth.security.oauth2poc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.UUID;

import static java.lang.Boolean.TRUE;

@Slf4j
@Service
public class EmailService implements IEmailService {

    @Value("${default.sender}")
    private String sender;
    @Value("${default.url}")
    private String contextPath;

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private  JavaMailSender javaMailSender;
    @Autowired
    private  UserService userService;

    @Override
    public void sendHtmlEmail(MimeMessage message) {
        log.info("Sending email...");
        javaMailSender.send(message);
        log.info("Email has sent with success.");
    }

    @Override
    public void sendConfirmationHtmlEmail(User user, TokenVerification tokenVerification, boolean emailConfirmation) {
        try {
            MimeMessage mimeMessage = prepareMimeMessageFromUser(user, tokenVerification, emailConfirmation);
            sendHtmlEmail(mimeMessage);
        }catch (MessagingException ex){
            throw new ResourceNotFoundException("Error trying to send email.");
        }
    }

    private MimeMessage prepareMimeMessageFromUser(User user, TokenVerification tokenVerification, boolean  emailConfirmation) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setFrom(sender);
        helper.setSubject("User confirmation");
        helper.setSentDate(new Date(System.currentTimeMillis()));
        helper.setText(htmlFromTemplate(user, tokenVerification, emailConfirmation), TRUE);
        helper.setTo(user.getEmail());

        if (emailConfirmation == false){
            helper.setSubject("Reset password");
        }
        return mimeMessage;
    }

    private String htmlFromTemplate(User user, TokenVerification tokenVerification, boolean  emailConfirmation){
        String token = UUID.randomUUID().toString();

        if (tokenVerification == null){
            this.userService.createVerificationTokenForUser(token, user);
        }else{
            token = tokenVerification.getToken();
        }
        String confirmationUrl = this.contextPath +  "/api/public/registration/users/confirmation?token=" + token;
        if (emailConfirmation == false){
            confirmationUrl = this.contextPath +  "/api/public/registration/users/change-password?id=" + user.getId()+ "&token=" + token;
        }
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("confirmationUrl", confirmationUrl);
        return templateEngine.process("email/registerUser", context);

    }

}
