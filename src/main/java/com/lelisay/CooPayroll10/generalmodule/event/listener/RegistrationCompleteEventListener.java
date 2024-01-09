package com.lelisay.CooPayroll10.generalmodule.event.listener;

import com.lelisay.CooPayroll10.generalmodule.event.RegistrationCompleteEvent;
import com.lelisay.CooPayroll10.coremodule.user.user.User;
import com.lelisay.CooPayroll10.coremodule.user.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


import java.io.UnsupportedEncodingException;
import java.util.UUID;
@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements
        ApplicationListener<RegistrationCompleteEvent> {

    private final UserService userService;
    private final JavaMailSender mailSender;
    private User newUser;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //1 get the newly created user
        newUser=event.getUser();
        //2 create verification token for the user
            String verificationToken = UUID.randomUUID().toString();
        //3 save verification token
            userService.saveUserVerificationToken(newUser,verificationToken);
        //4 build the verification URL to be sent to user
            String url = event.getApplicationUrl()+"/api/v1/register/verify-email?token="+verificationToken;
        //5 send the email
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify your registration :  {}", url);
    }


    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException, MessagingException {

        String subject = "Email Verification";
        String senderName = "Coopayroll Security";
        String mailContent = "<p> Hello, "+ newUser.getFirstName()+ ", </p>"+
                "<p>Thank you for registering with us,"+"" +
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
                "<p> Thank you <br> Coopayroll Security Team";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("coopayroll@gmail.com", senderName);
        messageHelper.setTo(newUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

    public void sendPasswordResetEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Password Reset Verification";
        String senderName = "Coopayroll Security";
        String mailContent = "<p> Hello, "+ newUser.getFirstName()+ ", </p>"+
                "<p>We're reaching out to confirm whether you initiated a password change. " +
                "If this action wasn't undertaken by you, please don't hesitate to inform us ,"+""
                +"<h3>We're reaching out to confirm whether you initiated a password change. <h3>"+
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\"" +url+ "\">Click the link to reset your password</a>"+
                "<p> Thank you <br> Coopayroll Security Team";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("coopayroll@gmail.com", senderName);
        messageHelper.setTo(newUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

}
