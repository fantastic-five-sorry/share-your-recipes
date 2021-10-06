package com.fantasticfour.shareyourrecipes.login;

import java.security.Principal;

import com.fantasticfour.shareyourrecipes.domains.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
// @RequestMapping("/api")
public class LoginController {

    // @
    @GetMapping("/home")
    public String getHome() {
        return "index";
    }

    @GetMapping("/")
    public String getHom2() {
        return "index";
    }

    @GetMapping("/ui/login")
    public String uiLogin() {
        return "login/login";
    }

    @GetMapping("/ui/signup")
    public String uiSignUp(Model model) {
        model.addAttribute("user", new User());
        return "login/sign-up";
    }
}
