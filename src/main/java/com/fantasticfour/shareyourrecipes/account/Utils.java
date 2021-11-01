package com.fantasticfour.shareyourrecipes.account;

import java.util.Optional;

import com.fantasticfour.shareyourrecipes.configs.UserPrincipal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import com.fantasticfour.shareyourrecipes.account.UserRepo;
import com.fantasticfour.shareyourrecipes.domains.enums.RecipeStatus;
import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.recipes.dtos.CreateRecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.UpdateRecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.repositories.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

@Component
public class Utils {
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

    @Value("${lvl.app.maxSlugRandomStringLength}")
    private static int SHORT_ID_LENGTH;

    private final static Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private final static Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String toSlug(String input) {
        String shortId = RandomStringUtils.randomAlphanumeric(SHORT_ID_LENGTH);
        input = input + " " + shortId;
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return normalized.toLowerCase(Locale.ENGLISH);
    }
}
