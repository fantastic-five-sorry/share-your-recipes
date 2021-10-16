package com.fantasticfour.shareyourrecipes.tokens;

import java.time.LocalDateTime;
import java.util.Optional;

import com.fantasticfour.shareyourrecipes.domains.auth.Token;
import com.fantasticfour.shareyourrecipes.domains.enums.ETokenPurpose;

public interface TokenService {
    Token findByToken(String token, ETokenPurpose purpose);

    Optional<Token> findUserToken(String email, String token, ETokenPurpose purpose);

    int updateTokenUsedAt(String token, ETokenPurpose purpose);

    Token save(Token Token);

    void purgeExpiredTokens(LocalDateTime now);

}
