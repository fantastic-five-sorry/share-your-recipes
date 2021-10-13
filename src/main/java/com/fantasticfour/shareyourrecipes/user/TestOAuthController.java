package com.fantasticfour.shareyourrecipes.user;

import java.security.Principal;
import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.User;
import com.fantasticfour.shareyourrecipes.user.dtos.UserInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestOAuthController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserService userService;

    @GetMapping("/test/user")
    public Principal user(Principal principal) {
        return principal;
    }

    @GetMapping("/test/user1")
    public UserInfo user1(Authentication authentication) {

        return getUserInfoFromAuthentication(authentication);
    }

    @GetMapping("/test/user2")
    public UserPrincipal user2(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
            return userDetails;
        }
        return null;
    }

    // @GetMapping("/test/me")
    // public UserInfo test(Authentication authentication) {
    //     return userService.;
    // }

    private UserInfo getUserInfoFromAuthentication(Authentication authentication) {
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
        if (userEmail == null)
            return null;
        return userService.getUserInfoByEmail(userEmail);
    }
}
