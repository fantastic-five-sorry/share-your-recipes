package com.fantasticfour.shareyourrecipes.account;

import java.security.Principal;

import com.fantasticfour.shareyourrecipes.account.dtos.UserInfo;
import com.fantasticfour.shareyourrecipes.configs.UserPrincipal;

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
    // return userService.;
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

    @GetMapping("/test-user-principle")
    public String testUserPrinciple(Authentication authentication) {
        // Authentication authentication =
        // SecurityContextHolder.getContext().getAuthentication();
        // com.fantasticfour.shareyourrecipes.account.UserPrincipal oauthUser =
        // (com.fantasticfour.shareyourrecipes.account.UserPrincipal) authentication
        // .getPrincipal();
        // System.out.println(oauthUser.getAttribute("email").toString());
        // model.addAttribute("your_email", uid.toString());
        // model.addAttribute("userInfo", userService.getUserInfoById(uid));
        // System.out.println();
        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            return "not auth";
        }
        if (authentication.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
            return userDetails.getFullName();
        }
        return "NOT Auth";
    }

}
