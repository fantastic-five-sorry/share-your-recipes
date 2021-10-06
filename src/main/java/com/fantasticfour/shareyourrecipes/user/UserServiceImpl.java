package com.fantasticfour.shareyourrecipes.user;

import java.time.LocalDateTime;
import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.ERole;
import com.fantasticfour.shareyourrecipes.domains.EmailConfirmToken;
import com.fantasticfour.shareyourrecipes.domains.ForgotPasswordToken;
import com.fantasticfour.shareyourrecipes.domains.Role;
import com.fantasticfour.shareyourrecipes.domains.User;
import com.fantasticfour.shareyourrecipes.tokens.EmailConfirmTokenRepo;
import com.fantasticfour.shareyourrecipes.tokens.ForgotPasswordTokenRepo;
import com.fantasticfour.shareyourrecipes.user.payload.SignUpRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void blockUser(SignUpRequest request) {
        // TODO Auto-generated method stub

    }

    @Override
    public void enableUser(SignUpRequest request) {
        // TODO Auto-generated method stub

    }

    @Override
    public void getUser(SignUpRequest request) {

    }

    @Override
    public void signUp(SignUpRequest request) {
        userRepo.save(new User(request.getEmail(), passwordEncoder.encode(request.getPassword()), request.getName()));
    }

    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private EmailConfirmTokenRepo emailConfirmTokenRepo;
    @Autowired
    private ForgotPasswordTokenRepo forgotTokenRepo;
    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public User saveUser(User user) {
        // log.info("Saving new user {} to the database", user.getFirstName());
        // user.setPassword(passwordEncoder.encode(user.getPassword()));
        // return userRepo.save(user);
        return null;
    };

    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());

        return roleRepo.save(role);

    };

    public void addRoleToUser(String username, ERole roleName) {
        // log.info("Saving new role {} to user {} to the database",
        // roleName.toString(), username);

        // User user = userRepo.findByUsername(username);
        // Role role = roleRepo.findByName(roleName);

        // user.getRoles().add(role);
        // userRepo.save(user);
    };

    public User getUser(String username) {
        log.info("Getting user {} to the database", username);

        // return userRepo.findByUsername(username);

        return null;
    };

    public List<User> getUsers() {
        return userRepo.findAll();
    };

    public Boolean isExistsUserByEmail(String email) {
        return userRepo.isExistsUserByEmail(email);
    };

    // public Boolean isExistsUserByUsername(String username) {
    // return userRepo.isExistsUserByUsername(username);
    // };

    public void verifyEmailByToken(String token) {
        EmailConfirmToken confirmationToken = emailConfirmTokenRepo.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            return;
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        userRepo.activateUser(confirmationToken.getUser().getEmail());
        emailConfirmTokenRepo.updateConfirmedAt(token, LocalDateTime.now());
    };

    public void activateUser(String email) {
        User user = userRepo.findByEmail(email).get();
        if (user != null) {
            user.setEnable(true);
            userRepo.save(user);
        }
    }

    public User getUserByEmail(String email) {

        return userRepo.findByEmail(email).get();
    };

    public void resetPasswordByToken(String token, String newPassword) {
        ForgotPasswordToken forgotToken = forgotTokenRepo.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));
        if (forgotToken.getResetPasswordAt() != null) {
            throw new IllegalStateException("token already confirmed");
        }
        LocalDateTime expiredAt = forgotToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        forgotTokenRepo.updateResetAt(token, LocalDateTime.now());
        userRepo.resetPassword(forgotToken.getUser().getId(), passwordEncoder.encode(newPassword));
    }

}
