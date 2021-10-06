package com.fantasticfour.shareyourrecipes.tokens;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.domains.ForgotPasswordToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ForgotPasswordTokenRepo extends JpaRepository<ForgotPasswordToken, Long> {
    Optional<ForgotPasswordToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE ForgotPasswordToken c " + "SET c.resetPasswordAt = ?2 " + "WHERE c.token = ?1")
    int updateResetAt(String token, LocalDateTime confirmedAt);
}
