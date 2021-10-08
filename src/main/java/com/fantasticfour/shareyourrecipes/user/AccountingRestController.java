package com.fantasticfour.shareyourrecipes.user;

import javax.servlet.http.HttpServletRequest;

import com.fantasticfour.shareyourrecipes.domains.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account/")
public class AccountingRestController {

    @Autowired
    UserService userService;

    @PostMapping("/email-verify")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        try {
            userService.verifyEmailByToken(token);
            return null;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("error: " + e.getMessage());

        }
    }

    @GetMapping("/request-resend-verify-email")
    public ResponseEntity<?> reSendConfirmEmail(@RequestParam("email") String email) {

        User user = userService.getUserByEmail(email);
        if (user != null) {
            if (user.isEnable()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("error: " + "account already activated");

            }
            // EmailConfirmToken token = new EmailConfirmToken(UUID.randomUUID().toString(),
            // LocalDateTime.now(),
            // LocalDateTime.now().plus(ConfirmTokenExpireInMinutes, ChronoUnit.MINUTES),
            // user);
            // emailConfirmTokenService.save(token);
            // // send email
            // emailSender.sendConfirmEmail(token);
            // return ResponseEntity.ok(new MessageResponse("Successfully send activated
            // email"));
            return null;

        } else
            return ResponseEntity.badRequest().body("error: " + "Not found username");
    }
    // @PostMapping("/validate-forgot-token")
    // public ResponseEntity<?> verifyForgotPasswordToken(@RequestParam("token")
    // String token) {

    // Optional<ForgotPasswordToken> tokenOpt =
    // forgotTokenService.findByToken(token);
    // if (tokenOpt.isPresent()) {
    // if (tokenOpt.get().getResetPasswordAt() == null)
    // // return ResponseEntity.ok(new MessageResponse("Reset Password Token
    // // validated"));
    // if (tokenOpt.get().getExpiresAt().isBefore(LocalDateTime.now()))
    // // return ResponseEntity.ok(new MessageResponse("Reset Password Token
    // // expired"));

    // return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("error:
    // token already used");
    // }
    // return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("error: Not
    // found token");

    // }

    @PostMapping("/request-forgot-password-email")
    public String sendForgotEmail(HttpServletRequest request) {
        String email = request.getParameter("email");
        User user = userService.getUserByEmail(email);
        if (user != null) {
            // ForgotPasswordToken token = new
            // ForgotPasswordToken(UUID.randomUUID().toString(), LocalDateTime.now(),
            // LocalDateTime.now().plus(ConfirmTokenExpireInMinutes, ChronoUnit.MINUTES),
            // user);
            // forgotTokenService.save(token);
            // // send email
            // emailSender.sendForgotEmail(token);
            // return ResponseEntity.ok(new MessageResponse("Successfully send forgot
            // email"));
            return null;

        } else
            // return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found user with
            // email " + email);
            return null;
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

    // private String getAppUrl(HttpServletRequest request) {
    // return "http://" + request.getServerName() + ":" + request.getServerPort() +
    // request.getContextPath();
    // }
}