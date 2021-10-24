package com.fantasticfour.shareyourrecipes.account;

import java.util.Optional;

import com.fantasticfour.shareyourrecipes.configs.UserPrincipal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {
    public static String getUserEmailFromAuthentication(Authentication authentication) {
        String userEmail = null;

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            userEmail = userDetails.getUsername();
        }

        if (authentication.getPrincipal() instanceof org.springframework.security.oauth2.core.user.DefaultOAuth2User) {
            org.springframework.security.oauth2.core.user.DefaultOAuth2User userDetails = (org.springframework.security.oauth2.core.user.DefaultOAuth2User) authentication
                    .getPrincipal();
            userEmail = userDetails.getAttribute("email");
        }
        return userEmail;
    }

    public static Optional<Long> getIdFromRequest(Authentication authentication) {

        if (authentication.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
            return Optional.of(userDetails.getId());
        }
        return null;
    }
}
