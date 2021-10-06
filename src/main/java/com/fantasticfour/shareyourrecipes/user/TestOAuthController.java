package com.fantasticfour.shareyourrecipes.user;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestOAuthController {
    @GetMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }
}
