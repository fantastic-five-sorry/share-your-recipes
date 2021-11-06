package com.fantasticfour.shareyourrecipes.configs;

import java.util.Optional;

import org.slf4j.*;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {
    Logger logger = LoggerFactory.getLogger(AuditorAwareImpl.class);
    private static final String ADMIN = "ADMIN_LVL";

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.of(ADMIN);
        }

        com.fantasticfour.shareyourrecipes.configs.UserPrincipal oauthUser = (com.fantasticfour.shareyourrecipes.configs.UserPrincipal) authentication
                .getPrincipal();
        String userEmail = oauthUser.getId().toString();
        logger.info("Email: " + userEmail);
        return Optional.of(userEmail);
    }
}