package com.fantasticfour.shareyourrecipes.account;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.account.dtos.SignUpDto;
import com.fantasticfour.shareyourrecipes.account.events.SendTokenEmailEvent;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.enums.ERole;
import com.fantasticfour.shareyourrecipes.domains.enums.ETokenPurpose;
import com.fantasticfour.shareyourrecipes.tokens.TokenService;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {
    Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    MessageSource messageSource;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    @Autowired
    ApplicationEventPublisher eventPublisher;
    @Autowired
    UserUtils userUtils;

    @GetMapping("/new-verification-email")
    public String requestNewEmailVerification(@RequestParam(name = "email", required = false) String email) {
        logger.info("request new v email from " + email);
        if (email != null) {
            User user = userService.getUserByEmail(email);
            if (user != null && !user.isBlocked()) {
                eventPublisher.publishEvent(new SendTokenEmailEvent(user, ETokenPurpose.VERIFY_EMAIL));
                return "login/new-verification-email";
            }
        }
        return "redirect:/404";
    }

    @GetMapping("/account/new-verification")
    public String requestNewEmailVerification2() {
        return "login/request-verification-email-form";
    }

    @GetMapping("/register-success")
    private String viewSuccessSignupPage() {

        return "login/register-success";
    }

    @GetMapping("/account/verify-email")
    public String confirmEmail(@RequestParam(name = "token", required = false) String token, Model model) {
        try {
            if (token != null) {
                userService.verifyEmailByToken(token);
                return "redirect:/login?success";
            } else {
                return "404";
            }

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login/email-verified";
        }
    }

    @GetMapping("/account/forgot-password")
    public String viewForgotPasswordRequestPage(Model model, HttpServletRequest request) {

        try {
            String token = request.getParameter("token");
            if (token != null) {
                tokenService.getValidToken(token, ETokenPurpose.RESET_PASSWORD);
                return "login/reset-password";

            }
            return "login/forgot-password-form";

        } catch (Exception e) {

            // model.addAttribute("token", token);
            model.addAttribute("message", e.getMessage());
            return "login/forgot-password-form";
        }
    }

    @GetMapping("/account/reset-password")
    public String viewResetPasswordPage(@RequestParam(name = "token", required = false) String token, Model model) {
        try {
            // User me = userService.getValidUserByEmail(email);
            // if (me != null) {
            // model.addAttribute("message", "Da gui tin nhan");
            // eventPublisher.publishEvent(new SendTokenEmailEvent(me,
            // ETokenPurpose.FORGOT_PASSWORD));
            // return "login/forgot-password-form";
            // }
            // model.addAttribute("message", "Fail");
            // if (token == null)
            // return "404";
            return "login/reset-password";

        } catch (Exception e) {

            model.addAttribute("message", e.getMessage());
            return "login/forgot-password-form";
        }
    }

    @GetMapping("/account/change-password")
    @PreAuthorize("isAuthenticated()")
    public String viewChangePasswordPage() {

        return "login/change-password";
    }

    @GetMapping(value = { "/logout" })
    public String logoutDo(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        HttpSession session = request.getSession(false);
        session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        for (Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }

        return "redirect:/login?logout";
    }

    @GetMapping("request-success")
    private String viewRequestResetPwSuccessPage() {
        return "login/forgot-pw-email-success";
    }
}
