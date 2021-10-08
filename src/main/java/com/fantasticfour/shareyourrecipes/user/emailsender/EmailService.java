package com.fantasticfour.shareyourrecipes.user.emailsender;

import com.fantasticfour.shareyourrecipes.domains.Token;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendEmail(SimpleMailMessage email);

    // void sendConfirmEmail(EmailConfirmToken token);

    // void sendForgotEmail(ForgotPasswordToken token);

    void sendTokenEmail(Token token);

    void testSendEmail(String to, String content);

    String buildEmail(String toEmail, String token);
}
