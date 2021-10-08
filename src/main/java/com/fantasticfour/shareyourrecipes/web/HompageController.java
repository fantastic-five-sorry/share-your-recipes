package com.fantasticfour.shareyourrecipes.web;

import java.security.Principal;

import com.fantasticfour.shareyourrecipes.domains.User;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
// @RequestMapping("/api")
public class HompageController {

    // @
    @GetMapping("/home")
    public String getHome() {
        return "index";
    }

    @GetMapping("/")
    public String getHom2() {
        return "index";
    }

    @GetMapping("/login")
    public String uiLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "login/login";
        }

        return "redirect:/";

    }

    @GetMapping("/my-profile")
    public String myProfile(Principal principal, Model model) {
        model.addAttribute("your_email", principal.getName());
        return "profile/my-profile";
    }

    @GetMapping("/signup")
    public String uiSignUp(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "login/login";
        }
        model.addAttribute("user", new User());
        return "login/sign-up";
    }

}
