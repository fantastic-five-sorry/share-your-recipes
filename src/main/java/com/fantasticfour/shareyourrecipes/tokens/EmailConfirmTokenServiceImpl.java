package com.fantasticfour.shareyourrecipes.tokens;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.fantasticfour.shareyourrecipes.domains.EmailConfirmToken;

@Service
public class EmailConfirmTokenServiceImpl implements EmailConfirmTokenService {

    final private EmailConfirmTokenRepo emailConfirmTokenRepo;

    public EmailConfirmTokenServiceImpl(EmailConfirmTokenRepo emailConfirmTokenRepo) {
        this.emailConfirmTokenRepo = emailConfirmTokenRepo;
    }

    public Optional<EmailConfirmToken> findByToken(String token) {
        return emailConfirmTokenRepo.findByToken(token);
    };

    public int updateConfirmedAt(String token) {
        return emailConfirmTokenRepo.updateConfirmedAt(token, LocalDateTime.now());
    };

    public void save(EmailConfirmToken emailConfirmToken) {
        emailConfirmTokenRepo.save(emailConfirmToken);
    };

}
