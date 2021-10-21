package com.fantasticfour.shareyourrecipes.account.emailsender;

import com.fantasticfour.shareyourrecipes.domains.auth.Token;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendEmail(SimpleMailMessage email);
    void sendTokenEmail(Token token);
    String buildVerifyEmail(String toEmail, String token);
    String buildForgotPwEmail(String toEmail, String token);
}
