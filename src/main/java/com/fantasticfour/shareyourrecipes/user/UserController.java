package com.fantasticfour.shareyourrecipes.user;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.User;
import com.fantasticfour.shareyourrecipes.user.payload.SignUpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@Controller
public class UserController {

    @Autowired
    UserRepo userRepo;

    @GetMapping("signup")
    public String showRegistrationForm(WebRequest request, Model model) {
        SignUpRequest srequest = new SignUpRequest();
        model.addAttribute("request", srequest);
        return "sign-up";
    }

    @Controller
    public class GreetingController {

        @GetMapping("/greeting")
        public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                Model model) {
            model.addAttribute("name", name);
            return "greeting";
        }

    }

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepo.save(user);

        return "register_success";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }
}
