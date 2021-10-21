package com.fantasticfour.shareyourrecipes.account;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.fantasticfour.shareyourrecipes.account.dtos.ChangePasswordDto;
import com.fantasticfour.shareyourrecipes.account.dtos.ResetPasswordDto;
import com.fantasticfour.shareyourrecipes.account.dtos.SignUpDto;
import com.fantasticfour.shareyourrecipes.account.events.SendTokenEmailEvent;
import com.fantasticfour.shareyourrecipes.configs.UserPrincipal;
import com.fantasticfour.shareyourrecipes.domains.auth.Token;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.enums.ETokenPurpose;
import com.fantasticfour.shareyourrecipes.tokens.TokenService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account/")
public class AccountRestController {
    Logger logger = org.slf4j.LoggerFactory.getLogger(AccountRestController.class);
    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @GetMapping("/hi")
    private String sayHi() {
        return "hello world";
    }

    @PostMapping("/email-verify")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        try {
            userService.verifyEmailByToken(token);
            return null;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("error: " + e.getMessage());

        }
    }

    @PostMapping("/request-resend-verify-email")
    public ResponseEntity<?> reSendConfirmEmail(@RequestParam("email") String email) {

        User user = userService.getUserByEmail(email);
        if (user != null) {
            if (user.isEnabled()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("error: " + "Account already activated");
            }
            if (user.isBlocked()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("error: " + "Account was blocked");
            }
            eventPublisher.publishEvent(new SendTokenEmailEvent(user, ETokenPurpose.VERIFY_EMAIL));
            // send email
            // emailSender.sendConfirmEmail(token);
            return ResponseEntity.ok().body("Successfully send activated email");
        } else
            return ResponseEntity.badRequest().body("error: " + "Not found this email");
    }

    @PostMapping("/validate-forgot-token")
    public ResponseEntity<?> verifyForgotPasswordToken(@RequestParam("token") String token) {

        try {
            Token tokenOpt = tokenService.getValidToken(token, ETokenPurpose.RESET_PASSWORD);
            return ResponseEntity.ok().body("success: Token expired at" + tokenOpt.getExpiresAt());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("error: " + e.getMessage());
        }

    }

    @PostMapping("/request-forgot-password-email")
    public ResponseEntity<?> sendForgotEmail(HttpServletRequest request) {
        try {
            String email = request.getParameter("email");
            User user = userService.getValidUserByEmail(email);
            eventPublisher.publishEvent(new SendTokenEmailEvent(user, ETokenPurpose.RESET_PASSWORD));
            return ResponseEntity.ok().body("Successfully send forgot email");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> handleResetPassword(@RequestBody ResetPasswordDto request) {
        logger.info("call rs password ");
        try {
            userService.resetPassword(request);
            return ResponseEntity.ok("Successfully reset password");
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error: " + e.getMessage());
        }
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> handleChangePassword(@RequestBody ChangePasswordDto request,
            Authentication authentication) {
        try {
            Long userId = getIdFromRequest(authentication).orElseThrow(() -> new IllegalStateException("not auth"));
            userService.changePassword(userId, request);
            return ResponseEntity.ok("Successfully changed password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error: " + e.getMessage());

        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> handleSignUp(@RequestBody SignUpDto request) {

        try {
            userService.registerNewAccount(request);
            return ResponseEntity.ok("Successfully registered new account");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error: " + e.getMessage());
        }
    }

    @PostMapping("/exists-email")
    public ResponseEntity<?> handleSignUp(@RequestParam("email") String email) {

        try {
            if (userService.getUserByEmail(email) == null)
                return ResponseEntity.ok("Email not exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email exists");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error: " + e.getMessage());
        }
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    private Optional<Long> getIdFromRequest(Authentication authentication) {

        if (authentication.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
            return Optional.of(userDetails.getId());
        }
        return null;
    }
}
