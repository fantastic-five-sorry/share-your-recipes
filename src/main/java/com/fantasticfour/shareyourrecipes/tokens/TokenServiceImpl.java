package com.fantasticfour.shareyourrecipes.tokens;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;

import com.fantasticfour.shareyourrecipes.domains.auth.Token;
import com.fantasticfour.shareyourrecipes.domains.enums.ETokenPurpose;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    final EntityManager entityManager;
    final CriteriaBuilder criteriaBuilder;
    final TokenRepo tokenRepo;

    @Autowired
    public TokenServiceImpl(EntityManager entityManager, TokenRepo tokenRepo) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.tokenRepo = tokenRepo;
    }

    public Token getValidToken(String tokenString, ETokenPurpose purpose) {
        Token token = tokenRepo.findByToken(tokenString, purpose)
                .orElseThrow(() -> new IllegalStateException("Token not found"));
        if (token.getTokenUsedAt() != null) {
            throw new IllegalStateException("Token already confirmed");
        }
        LocalDateTime expiredAt = token.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }
        return token;
    };

    public Optional<Token> getUserTokens(String email, String token, ETokenPurpose purpose) {
        // return tokenRepo.findByToken(token);
        TypedQuery<Token> query = entityManager.createQuery(
                "SELECT t FROM Token t JOIN FETCH t.user WHERE t.user.email = :email AND t.token=:token AND t.purpose=:purpose",
                Token.class);

        query.setParameter("email", email);
        query.setParameter("token", token);
        query.setParameter("purpose", purpose);

        return Optional.of(query.getSingleResult());
    };

    @Override
    public Token save(Token token) {

        return tokenRepo.saveAndFlush(token);

    }

    @Override
    public int updateTokenUsedAt(String token, ETokenPurpose purpose) {
        return tokenRepo.updateTokenUsedAt(token, LocalDateTime.now(), purpose);

    }

    public void purgeExpiredTokens(LocalDateTime now) {

        tokenRepo.deleteAllExpiredSince(now);

    };
}
