package com.fantasticfour.shareyourrecipes.tokens;

import java.util.Optional;

import com.fantasticfour.shareyourrecipes.domains.EmailConfirmToken;

public interface EmailConfirmTokenService {
    Optional<EmailConfirmToken> findByToken(String token);

    int updateConfirmedAt(String token);

    void save(EmailConfirmToken emailConfirmToken);
}
