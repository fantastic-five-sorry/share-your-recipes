package com.fantasticfour.shareyourrecipes.tokens;

import java.time.LocalDateTime;
import java.util.Optional;

import com.fantasticfour.shareyourrecipes.domains.auth.Token;
import com.fantasticfour.shareyourrecipes.domains.enums.ETokenPurpose;

public interface TokenService {
    Token getValidToken(String token, ETokenPurpose purpose);

    Optional<Token> getUserTokens(String email, String token, ETokenPurpose purpose);

    int updateTokenUsedAt(String token, ETokenPurpose purpose);

    Token save(Token Token);

    void purgeExpiredTokens(LocalDateTime now);



}
