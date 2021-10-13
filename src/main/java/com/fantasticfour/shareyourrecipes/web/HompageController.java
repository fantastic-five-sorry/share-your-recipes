package com.fantasticfour.shareyourrecipes.web;

import java.security.Principal;

import com.fantasticfour.shareyourrecipes.domains.User;
import com.fantasticfour.shareyourrecipes.domains.enums.ETokenPurpose;
import com.fantasticfour.shareyourrecipes.user.UserService;
import com.fantasticfour.shareyourrecipes.user.events.SendTokenEmailEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
// @RequestMapping("/api")
public class HompageController {

    Logger logger = LoggerFactory.getLogger(HompageController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "login/login";
        }
        com.fantasticfour.shareyourrecipes.user.UserPrincipal oauthUser = (com.fantasticfour.shareyourrecipes.user.UserPrincipal) auth
                .getPrincipal();
        // System.out.println(oauthUser.getAttribute("email").toString());
        model.addAttribute("your_email", oauthUser.getFullName());
        model.addAttribute("userInfo", userService.getUserInfoByEmail(oauthUser.getAttribute("email")));
        // model.addAttribute("your_name", principal.());
        return "profile/my-profile";
    }

    @GetMapping("/signup")
    public String uiSignUp(Model model) {
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // if (auth == null || auth instanceof AnonymousAuthenticationToken) {
        // return "login/login";
        // }
        model.addAttribute("user", new User());
        return "login/sign-up";
    }

    @GetMapping("/international")
    public String getInternationalPage() {
        return "international";
    }

    @GetMapping("/new-verification-email")
    public String requestNewEmailVerification(@RequestParam(name = "email", required = false) String email) {
        logger.info("request new v email from " + email);
        if (email != null) {
            User user = userService.getUserByEmail(email);
            if (user != null) {
                eventPublisher.publishEvent(new SendTokenEmailEvent(user, ETokenPurpose.VERIFY_EMAIL));
                return "login/new-verification-email";
            }
        }
        return "redirect:/404";
    }

    @GetMapping("/404")
    public String notfoundPage() {
        return "404";
    }

    @GetMapping("/test-role")
    public String testRole(Model model) {
        model.addAttribute("adminMessage", "Admin content available");
        model.addAttribute("staffMessage", "Staff content available");
        model.addAttribute("userMessage", "User content available");
        return "roleHierarchy";
    }
}
