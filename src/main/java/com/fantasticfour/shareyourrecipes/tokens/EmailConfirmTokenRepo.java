package com.fantasticfour.shareyourrecipes.tokens;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.domains.EmailConfirmToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface EmailConfirmTokenRepo extends JpaRepository<EmailConfirmToken, Long> {

    Optional<EmailConfirmToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE EmailConfirmToken c " + "SET c.confirmedAt = ?2 " + "WHERE c.token = ?1")
    int updateConfirmedAt(String token, LocalDateTime confirmedAt);

}
