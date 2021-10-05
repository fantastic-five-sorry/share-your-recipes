package com.fantasticfour.shareyourrecipes.emailsender;

import com.fantasticfour.shareyourrecipes.domains.EmailConfirmToken;
import com.fantasticfour.shareyourrecipes.domains.ForgotPasswordToken;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendEmail(SimpleMailMessage email);

    void sendConfirmEmail(EmailConfirmToken token);

    void sendForgotEmail(ForgotPasswordToken token);

    void testSendEmail(String to, String content);

    String buildEmail(String toEmail, String token);
}
