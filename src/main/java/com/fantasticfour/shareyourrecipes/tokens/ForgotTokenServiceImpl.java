package com.fantasticfour.shareyourrecipes.tokens;

import java.time.LocalDateTime;
import java.util.Optional;

import com.fantasticfour.shareyourrecipes.domains.ForgotPasswordToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForgotTokenServiceImpl implements ForgotTokenService {
    @Autowired
    private ForgotPasswordTokenRepo forgotTokenRepo;

    public Optional<ForgotPasswordToken> findByToken(String token) {
        return forgotTokenRepo.findByToken(token);
    };

    public int updateResetPasswordAt(String token) {
        return forgotTokenRepo.updateResetAt(token, LocalDateTime.now());
    };

    public void save(ForgotPasswordToken token) {
        forgotTokenRepo.save(token);
    };
}
