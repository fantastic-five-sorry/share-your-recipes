package com.fantasticfour.shareyourrecipes.user;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.enums.ERole;
import com.fantasticfour.shareyourrecipes.domains.enums.ETokenPurpose;
import com.fantasticfour.shareyourrecipes.tokens.TokenService;
import com.fantasticfour.shareyourrecipes.user.dtos.SignUpDto;
import com.fantasticfour.shareyourrecipes.user.events.SendTokenEmailEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountingController {
    @Autowired
    private MessageSource messages;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    UserUtils userUtils;

    @Controller
    public class GreetingController {

        @GetMapping("/greeting")
        public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                Model model) {
            model.addAttribute("name", name);
            return "greeting";
        }

    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("signUpRequest", new SignUpDto());

        return "signup_form";
    }

    @PostMapping("/process_register")
    @Transactional
    public String processRegister(User user, Model model, HttpServletRequest request) {
        if (userService.getUserByEmail(user.getEmail()) != null) {
            model.addAttribute("message", "Account existed, <a href='/ui/login'>login?</a> ");
            return "login/sign-up";
        }
        user.getRoles().add(roleRepo.findByName(ERole.ROLE_USER));

        User userSaved = userService.saveUser(user);

        eventPublisher.publishEvent(new SendTokenEmailEvent(userSaved, ETokenPurpose.VERIFY_EMAIL));

        return "login/register-success";

    }

    @GetMapping("/register-success")
    private String viewSuccessSignupPage() {
        return "login/register-success";
    }

    @GetMapping("/account/verify-email")
    public String confirmEmail(@RequestParam("token") String token, Model model) {
        try {
            userService.verifyEmailByToken(token);
            model.addAttribute("token", token);
            model.addAttribute("message", "OK");
            return "redirect::/login";

        } catch (Exception e) {

            // String errorMessage = "";
            // // model.addAttribute("token", token);
            // errorMessage = messages.getMessage("auth.message.disabled", null, locale);
            // errorMessage = String.format(errorMessage, failEmailUser);

            model.addAttribute("message", e.getMessage());
            return "login/email-verified";
        }
    }

    @GetMapping("/account/forgot-password")
    public String viewForgotPasswordRequestPage(Model model, HttpServletRequest request) {

        try {
            String token = request.getParameter("token");
            if (token != null) {
                tokenService.findByToken(token, ETokenPurpose.FORGOT_PASSWORD);
                return "login/reset-password";

            }
            return "login/forgot-password-request";

        } catch (Exception e) {

            // model.addAttribute("token", token);
            model.addAttribute("message", e.getMessage());
            return "login/forgot-password-request";
        }
    }

    @GetMapping("/account/request-forgot-password-email")
    public String requestForgotPasswordHandler(@RequestParam("email") String email, Model model) {
        try {
            User me = userService.getValidUserByEmail(email);
            if (me != null) {
                model.addAttribute("message", "Da gui tin nhan");
                eventPublisher.publishEvent(new SendTokenEmailEvent(me, ETokenPurpose.FORGOT_PASSWORD));
                return "login/forgot-password-request";
            }
            model.addAttribute("message", "Fail");
            return "login/forgot-password-request";

        } catch (Exception e) {

            // model.addAttribute("token", token);
            model.addAttribute("message", e.getMessage());
            return "login/forgot-password-request";
        }
    }

    @GetMapping("/account/reset-password-process")
    public String resetPasswordProcess(@RequestParam("email") String email, Model model) {
        try {
            User me = userService.getValidUserByEmail(email);
            if (me != null) {
                model.addAttribute("message", "Da gui tin nhan");
                eventPublisher.publishEvent(new SendTokenEmailEvent(me, ETokenPurpose.FORGOT_PASSWORD));
                return "login/forgot-password-request";
            }
            model.addAttribute("message", "Fail");
            return "login/forgot-password-request";

        } catch (Exception e) {

            model.addAttribute("message", e.getMessage());
            return "login/forgot-password-request";
        }
    }

    @GetMapping("/account/change-password")
    public String viewChangePasswordPage() {

        return "login/change-password";
    }
}
