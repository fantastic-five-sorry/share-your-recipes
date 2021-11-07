package com.fantasticfour.shareyourrecipes.account;

import javax.servlet.http.HttpServletRequest;

import com.fantasticfour.shareyourrecipes.account.events.SendTokenEmailEvent;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.enums.ETokenPurpose;
import com.fantasticfour.shareyourrecipes.storages.StorageService;
import com.fantasticfour.shareyourrecipes.tokens.TokenService;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/account")
@PreAuthorize("hasRole('ADMIN'")
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
    Utils userUtils;

    @Autowired
    @Qualifier("avatar")
    StorageService avatarStorageService;

    @GetMapping("/new-verification")
    public String requestNewEmailVerification2() {
        return "login/request-verification-email-form";
    }

    @GetMapping("/get-verification-email")
    public String requestNewEmailVerification(@RequestParam(name = "email", required = false) String email) {
        logger.info("request new v email from " + email);
        if (email != null) {
            User user = userService.getUserByEmail(email);
            if (user != null && !user.isBlocked()) {
                eventPublisher.publishEvent(new SendTokenEmailEvent(user, ETokenPurpose.VERIFY_EMAIL));
                return "login/forgot-pw-email-success";
            }
        }
        return "redirect:/404";
    }

    @GetMapping("/verify-email")
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

    @GetMapping("/forgot-password")
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

    @GetMapping("/reset-password")
    public String viewResetPasswordPage(Model model) {

        return "login/reset-password";

    }

    @GetMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public String viewChangePasswordPage() {

        return "login/change-password";
    }

    @GetMapping("/change-avatar")
    private String viewChangeAvatar() {
        return "profile/change-avatar";
    }

    @PostMapping("/change-avatar")
    public String handleChangeAvatar(Authentication authentication, @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
        logger.info("upload file wt: " + file.getContentType());
        try {
            if (file.getContentType().contains("image")) {
                String fileName = avatarStorageService.store(file);
                // update avt url to user
                String avatarUrl = "/storage/avatar/" + fileName;
                //
                Long uid = Utils.getIdFromRequest(authentication)
                        .orElseThrow(() -> new IllegalStateException("User not found"));

                userService.updateAvatar(uid, avatarUrl);

                logger.info("User " + uid + " has change the avatar by the file " + avatarUrl);

                redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + fileName + "!");
            } else {
                redirectAttributes.addFlashAttribute("message",
                        "Accept image type only. " + file.getOriginalFilename() + " dame dane!");
            }
            return "redirect:/my-profile";
        } catch (Exception e) {
            return "404";
        }
    }

}
