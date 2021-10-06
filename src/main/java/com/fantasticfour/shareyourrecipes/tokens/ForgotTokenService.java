package com.fantasticfour.shareyourrecipes.tokens;

import java.util.Optional;

import com.fantasticfour.shareyourrecipes.domains.ForgotPasswordToken;

public interface ForgotTokenService {
    Optional<ForgotPasswordToken> findByToken(String token);

    int updateResetPasswordAt(String token);

    void save(ForgotPasswordToken token);
}
