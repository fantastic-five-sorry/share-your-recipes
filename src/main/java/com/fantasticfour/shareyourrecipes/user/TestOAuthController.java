package com.fantasticfour.shareyourrecipes.user;

import java.security.Principal;
import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.User;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/test/users")
    public List<User> test() {
        return userRepo.findAll();
    }
}
