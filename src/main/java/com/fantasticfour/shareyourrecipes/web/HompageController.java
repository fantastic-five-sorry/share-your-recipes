package com.fantasticfour.shareyourrecipes.web;

import java.security.Principal;

import com.fantasticfour.shareyourrecipes.account.UserService;
import com.fantasticfour.shareyourrecipes.account.events.SendTokenEmailEvent;
import com.fantasticfour.shareyourrecipes.configs.UserPrincipal;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.enums.ETokenPurpose;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @PreAuthorize("isAuthenticated()")
    public String myProfile(Principal principal, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "login/login";
        }
        com.fantasticfour.shareyourrecipes.configs.UserPrincipal oauthUser = (com.fantasticfour.shareyourrecipes.configs.UserPrincipal) auth
                .getPrincipal();
        Long yourId = oauthUser.getId();
        if (yourId != null) {
            model.addAttribute("userInfo", userService.getUserInfoById(yourId));
            model.addAttribute("your_email", oauthUser.getFullName());
            return "profile/my-profile";

        }
        return "404";
        // model.addAttribute("your_name", principal.());
    }

    @GetMapping("/signup")
    public String uiSignUp() {
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // if (auth == null || auth instanceof AnonymousAuthenticationToken) {
        // return "login/login";
        // }
        // model.addAttribute("user", new User());
        return "login/sign-up";
    }

    @GetMapping("/international")
    public String getInternationalPage() {
        return "international";
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

    @GetMapping("/profile/{uid}")
    public String userProfile(@PathVariable("uid") Long uid, Model model) {
        // System.out.println(oauthUser.getAttribute("email").toString());
        model.addAttribute("your_email", uid.toString());
        model.addAttribute("userInfo", userService.getUserInfoById(uid));
        return "profile/my-profile";
    }

    @GetMapping("/change-avatar")
    public String changeAvt(Model model) {
        // System.out.println(oauthUser.getAttribute("email").toString());
        // model.addAttribute("your_email", uid.toString());
        // model.addAttribute("userInfo", userService.getUserInfoById(uid));
        return "profile/change-avatar";
    }

    @GetMapping("/newfunc")
    public String newfunc(Model model) {
        // System.out.println(oauthUser.getAttribute("email").toString());
        // model.addAttribute("your_email", uid.toString());
        // model.addAttribute("userInfo", userService.getUserInfoById(uid));
        return "testingFunc";
    }

}
