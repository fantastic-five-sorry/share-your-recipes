package com.fantasticfour.shareyourrecipes.user;

import java.security.Principal;
import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestOAuthController {

    @Autowired
    UserRepo userRepo;

    @GetMapping("/test/user")
    public Principal user(Principal principal) {
        return principal;
    }

    @GetMapping("/test/user1")
    public String user1(Authentication authentication) {

        return getUserEmailFromAuthentication(authentication);
    }

    @GetMapping("/test/user2")
    public org.springframework.security.oauth2.core.user.DefaultOAuth2User user2(Authentication authentication) {
        if (authentication.getPrincipal() instanceof org.springframework.security.oauth2.core.user.DefaultOAuth2User) {
            org.springframework.security.oauth2.core.user.DefaultOAuth2User userDetails = (org.springframework.security.oauth2.core.user.DefaultOAuth2User) authentication
                    .getPrincipal();
            return userDetails;
        }
        return null;
    }

    @GetMapping("/test/users")
    public List<User> test() {
        return userRepo.findAll();
    }

    private String getUserEmailFromAuthentication(Authentication authentication) {
        String userEmail = null;

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            userEmail = userDetails.getUsername();
        }

        if (authentication.getPrincipal() instanceof org.springframework.security.oauth2.core.user.DefaultOAuth2User) {
            org.springframework.security.oauth2.core.user.DefaultOAuth2User userDetails = (org.springframework.security.oauth2.core.user.DefaultOAuth2User) authentication
                    .getPrincipal();
            userEmail = userDetails.getAttribute("email");
            userEmail = userDetails.getName();
        }

        return userEmail;
    }
}
