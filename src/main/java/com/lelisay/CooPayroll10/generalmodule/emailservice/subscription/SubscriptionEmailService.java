package com.lelisay.CooPayroll10.generalmodule.emailservice.subscription;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;



@RequiredArgsConstructor
@Service
public class SubscriptionEmailService {

    private final JavaMailSender javaMailSender;

    public void sendSubscriptionEmailMessage(String to, String subject, String htmlBody) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true indicates HTML format

            javaMailSender.send(message);
        } catch (MessagingException e) {
            // Handle exception or log the error
            e.printStackTrace();
        }
    }
}
