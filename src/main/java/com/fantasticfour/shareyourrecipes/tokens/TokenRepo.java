package com.fantasticfour.shareyourrecipes.tokens;

import java.time.LocalDateTime;
import java.util.Optional;

import com.fantasticfour.shareyourrecipes.domains.auth.Token;
import com.fantasticfour.shareyourrecipes.domains.enums.ETokenPurpose;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {

    @Query("SELECT u FROM Token u WHERE u.token = :token AND u.purpose =:purpose")
    Optional<Token> findByToken(String token, ETokenPurpose purpose);

    @Transactional
    @Modifying
    @Query("UPDATE Token c " + "SET c.tokenUsedAt = ?2 " + "WHERE c.token = ?1 AND purpose = ?3")
    int updateTokenUsedAt(String token, LocalDateTime confirmedAt, ETokenPurpose purpose);

    @Modifying
    @Query("delete from Token t where t.expiresAt <= ?1")
    void deleteAllExpiredSince(LocalDateTime now);
}
