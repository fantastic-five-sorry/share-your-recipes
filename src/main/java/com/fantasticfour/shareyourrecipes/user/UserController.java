package com.fantasticfour.shareyourrecipes.user;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.domains.EmailConfirmToken;
import com.fantasticfour.shareyourrecipes.domains.ForgotPasswordToken;
import com.fantasticfour.shareyourrecipes.domains.User;
import com.fantasticfour.shareyourrecipes.emailsender.EmailService;
import com.fantasticfour.shareyourrecipes.tokens.EmailConfirmTokenService;
import com.fantasticfour.shareyourrecipes.tokens.ForgotTokenService;
import com.fantasticfour.shareyourrecipes.user.payload.SignUpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@Controller
public class UserController {
    @Value("${lvl.app.ConfirmTokenExpireInMinutes}")
    private Long ConfirmTokenExpireInMinutes;
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserService userService;
    @Autowired
    ForgotTokenService forgotTokenService;
    @Autowired

    EmailConfirmTokenService emailConfirmTokenService;
    @Autowired
    EmailService emailSender;

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
        model.addAttribute("user", new User());

        return "signup_form";
    }

    @PostMapping("/process_register")
    @Transactional
    public String processRegister(User user, Model model) {

        if (userService.getUserByEmail(user.getEmail()) != null) {
            model.addAttribute("message", "Account existed, <a href='/ui/login'>login?</a> ");
            return "login/sign-up";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepo.save(user);
        EmailConfirmToken token = new EmailConfirmToken(UUID.randomUUID().toString(), LocalDateTime.now(),
                LocalDateTime.now().plus(ConfirmTokenExpireInMinutes, ChronoUnit.MINUTES), user);
        emailConfirmTokenService.save(token);
        // send email
        emailSender.sendConfirmEmail(token);
        // emailSender.sendConfirmEmail(new EmailConfirmToken());
        return "register_success";

    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    @GetMapping("/activate")
    public ResponseEntity<?> confirmEmail(@RequestParam("token") String token) {
        try {
            userService.verifyEmailByToken(token);
            // return ResponseEntity.ok(new MessageResponse("Successfully activated
            // email"));
            return null;

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("error: " + e.getMessage());

        }
    }

    @PostMapping("/email-verify")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        try {
            userService.verifyEmailByToken(token);
            // return ResponseEntity.ok(new MessageResponse("Successfully verified
            // email"));\
            return null;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("error: " + e.getMessage());

        }
    }

    @PostMapping("/validate-forgot-token")
    public ResponseEntity<?> verifyForgotPasswordToken(@RequestParam("token") String token) {

        Optional<ForgotPasswordToken> tokenOpt = forgotTokenService.findByToken(token);
        if (tokenOpt.isPresent()) {
            if (tokenOpt.get().getResetPasswordAt() == null)
                // return ResponseEntity.ok(new MessageResponse("Reset Password Token
                // validated"));
                if (tokenOpt.get().getExpiresAt().isBefore(LocalDateTime.now()))
                    // return ResponseEntity.ok(new MessageResponse("Reset Password Token
                    // expired"));

                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("error: token already used");
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("error: Not found token");

    }

    @GetMapping("/request-resend-verify-email")
    public ResponseEntity<?> reSendConfirmEmail(@RequestParam("email") String email) {

        User user = userRepo.findByEmail(email).get();
        if (user != null) {
            if (user.isEnable()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("error: " + "account already activated");

            }
            EmailConfirmToken token = new EmailConfirmToken(UUID.randomUUID().toString(), LocalDateTime.now(),
                    LocalDateTime.now().plus(ConfirmTokenExpireInMinutes, ChronoUnit.MINUTES), user);
            emailConfirmTokenService.save(token);
            // send email
            emailSender.sendConfirmEmail(token);
            // return ResponseEntity.ok(new MessageResponse("Successfully send activated
            // email"));
            return null;

        } else
            return ResponseEntity.badRequest().body("error: " + "Not found username");
    }

    @PostMapping("/request-forgot-password-email")
    public ResponseEntity<?> sendForgotEmail(HttpServletRequest request) {
        String email = request.getParameter("email");
        User user = userService.getUserByEmail(email);
        if (user != null) {
            ForgotPasswordToken token = new ForgotPasswordToken(UUID.randomUUID().toString(), LocalDateTime.now(),
                    LocalDateTime.now().plus(ConfirmTokenExpireInMinutes, ChronoUnit.MINUTES), user);
            forgotTokenService.save(token);
            // send email
            emailSender.sendForgotEmail(token);
            // return ResponseEntity.ok(new MessageResponse("Successfully send forgot
            // email"));
            return null;

        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found user with email " + email);
    }

    // @PostMapping("/reset-password")
    // public ResponseEntity<?> handleResetPassword(@RequestBody
    // ResetPasswordRequest request) {

    // try {
    // userService.resetPasswordByToken(request.getToken(),
    // request.getNewPassword());
    // return ResponseEntity.ok(new MessageResponse("Successfully reset password"));
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("error: " +
    // e.getMessage());

    // }
    // }

}
