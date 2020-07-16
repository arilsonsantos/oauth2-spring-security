package br.com.example.auth.security.oauth2poc.service.email;

import br.com.example.auth.security.oauth2poc.domain.TokenVerification;
import br.com.example.auth.security.oauth2poc.domain.User;
import br.com.example.auth.security.oauth2poc.exceptions.ResourceNotFoundException;
import br.com.example.auth.security.oauth2poc.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.UUID;

public class AbstractEmailService implements IEmailService {

    @Value("${default.sender}")
    private String sender;
    @Value("${default.url}")
    private String contextPath;

    @Autowired
    private  TemplateEngine templateEngine;
    @Autowired
    private  JavaMailSender javaMailSender;
    @Autowired
    private  UserService userService;

    @Override
    public void sendHtmlEmail(MimeMessage message) throws MessagingException {
        javaMailSender.send(message);
    }

    @Override
    public void sendConfirmationHtmlEmail(User user, TokenVerification tokenVerification, int select) {
        try {
            MimeMessage mimeMessage = prepareMimeMessageFromUser(user, tokenVerification, select);
            sendHtmlEmail(mimeMessage);
        }catch (MessagingException ex){
            throw new ResourceNotFoundException("Error trying to send email.");
        }
    }

    protected MimeMessage prepareMimeMessageFromUser(User user, TokenVerification tokenVerification, int select) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setSubject("Testing confirmation");
        if (select == 1){
            mimeMessageHelper.setSubject("Reset password");
        }
        mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
        mimeMessageHelper.setText(htmlFromTemplate(user, tokenVerification, select), true);
        return mimeMessage;
    }

    protected String htmlFromTemplate(User user, TokenVerification tokenVerification, int select){
        String token = UUID.randomUUID().toString();

        if (tokenVerification == null){
            this.userService.createVerificationTokenForUser(token, user);
        }else{
            token = tokenVerification.getToken();
        }
        String confirmationUrl = this.contextPath +  "/api/public/registration/users/confirmation?token=" + token;
        if (select == 1){
            confirmationUrl = this.contextPath +  "/api/public/registration/users/change-password?id=" + user.getId()+ "&token=" + token;
        }
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("confirmationUrl", confirmationUrl);
        return templateEngine.process("email/registerUser", context);

    }

}
