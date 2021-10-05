package com.fantasticfour.shareyourrecipes.login;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @RequestMapping("/api")
public class LoginController {

    // @
    @GetMapping("/home")
    public String getHome() {
        return "Homepage";
    }

    @GetMapping("/")
    public String getHom2() {
        return "Homepage";
    }

    
}
