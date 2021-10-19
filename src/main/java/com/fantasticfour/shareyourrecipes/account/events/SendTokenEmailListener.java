package com.fantasticfour.shareyourrecipes.account.events;

import java.util.UUID;

import com.fantasticfour.shareyourrecipes.account.UserService;
import com.fantasticfour.shareyourrecipes.account.emailsender.EmailService;
import com.fantasticfour.shareyourrecipes.domains.auth.Token;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SendTokenEmailListener implements ApplicationListener<SendTokenEmailEvent> {
    Logger logger = LoggerFactory.getLogger(SendTokenEmailListener.class);

    @Autowired
    private UserService userService;

    @Autowired
    EmailService emailService;

    @Override
    public void onApplicationEvent(final SendTokenEmailEvent event) {
        this.confirmSignUp(event);

    }

    private void confirmSignUp(final SendTokenEmailEvent event) {
        logger.info("Confirm sign up");
        final String token = UUID.randomUUID().toString();
        Token tokenSaved = userService.createToken(event.getUser(), token, event.getEmailPurpose());
        emailService.sendTokenEmail(tokenSaved);
    }
}
