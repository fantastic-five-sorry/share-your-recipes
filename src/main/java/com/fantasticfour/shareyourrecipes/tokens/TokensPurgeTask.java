package com.fantasticfour.shareyourrecipes.tokens;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class TokensPurgeTask {

    Logger logger = LoggerFactory.getLogger(TokensPurgeTask.class);
    @Autowired
    TokenService tokenService;

    // @Autowired
    // ForgotTokenService passwordTokenService;

    @Scheduled(cron = "${purge.cron.expression}")
    public void purgeExpired() {
        logger.info("Purging expired tokens....");

        tokenService.purgeExpiredTokens(LocalDateTime.now());
        // passwordTokenService.purgeExpiredTokens(LocalDateTime.now());
        // passwordTokenRepository.deleteAllExpiredSince(now);
        // tokenRepository.deleteAllExpiredSince(now);
    }
}