package com.fantasticfour.shareyourrecipes.user;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.domains.auth.Role;
import com.fantasticfour.shareyourrecipes.domains.auth.Token;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.enums.ERole;
import com.fantasticfour.shareyourrecipes.domains.enums.ETokenPurpose;
import com.fantasticfour.shareyourrecipes.tokens.TokenService;
import com.fantasticfour.shareyourrecipes.user.dtos.ChangePasswordDto;
import com.fantasticfour.shareyourrecipes.user.dtos.ResetPasswordDto;
import com.fantasticfour.shareyourrecipes.user.dtos.SignUpDto;
import com.fantasticfour.shareyourrecipes.user.dtos.UserInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleRepo roleRepo;
    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${lvl.app.ConfirmTokenExpireInMinutes}")
    private Long ConfirmTokenExpireInMinutes;

    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenService tokenService;

    @Override
    @Transactional
    public void blockUser(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new IllegalStateException("Email not found"));
        user.setBlocked(true);
        userRepo.save(user);
    }

    @Override
    @Transactional
    public void unblockUser(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new IllegalStateException("Email not found"));
        user.setBlocked(false);
        userRepo.save(user);
    }

    @Override
    @Transactional
    public void enableUser(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new IllegalStateException("Email not found"));
        user.setEnabled(true);
        userRepo.save(user);
    }

    @Override
    public void registerNewAccount(SignUpDto request) {
        if (!request.getPassword().equals(request.getConfirmPassword()))
            throw new IllegalStateException("Confirm password not match");
        if (userRepo.findByEmail(request.getEmail()).isPresent())
            throw new IllegalStateException("Email exists");
        User newUser = new User(request.getEmail(), passwordEncoder.encode(request.getPassword()), request.getName());
        newUser.getRoles().add(roleRepo.findByName(ERole.ROLE_USER));
        userRepo.save(newUser);
    }

    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.saveAndFlush(user);
    };

    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());

        return roleRepo.save(role);

    };

    public void addRoleToUser(String email, ERole roleName) {
        log.info("Saving new role {} to user {} to the database", roleName.toString(), email);

        User user = userRepo.findByEmail(email).orElseThrow(() -> new IllegalStateException("Email not found"));
        Role role = roleRepo.findByName(roleName);

        user.getRoles().add(role);
        userRepo.save(user);
    };

    public User getEnabledUser(String email) {
        return userRepo.findEnabledUserByEmail(email);

    };

    public List<User> getUsers() {
        return userRepo.findAll();
    };

    public Boolean isExistsUserByEmail(String email) {
        return userRepo.isExistsUserByEmail(email);
    };

    public void verifyEmailByToken(String token) {
        Token confirmationToken = tokenService.findByToken(token, ETokenPurpose.VERIFY_EMAIL);

        if (confirmationToken.getTokenUsedAt() != null) {
            return;
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        userRepo.activateUser(confirmationToken.getUser().getEmail());
        tokenService.updateTokenUsedAt(token, ETokenPurpose.VERIFY_EMAIL);
    };

    public void activateUser(String email) {
        User user = userRepo.findByEmail(email).get();
        if (user != null) {
            user.setEnabled(true);
            userRepo.save(user);
        }
    }

    public User getValidUserByEmail(String email) {

        User user = userRepo.findByEmail(email).orElseThrow(() -> new IllegalStateException("Email not found"));

        if (user.isBlocked())
            throw new IllegalStateException("Email was blocked");
        if (!user.isEnabled())
            throw new IllegalStateException("Email was not verified");
        return user;
    };

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    public void resetPasswordByToken(String token, String newPassword) {
        Token forgotToken = tokenService.findByToken(token, ETokenPurpose.FORGOT_PASSWORD);
        tokenService.updateTokenUsedAt(token, ETokenPurpose.FORGOT_PASSWORD);
        userRepo.resetPassword(forgotToken.getUser().getId(), passwordEncoder.encode(newPassword));
    }

    @Override
    public Token createToken(User user, String token, ETokenPurpose purpose) {
        Token emailToken = new Token();
        emailToken.setUser(user);
        emailToken.setToken(token);
        emailToken.setPurpose(purpose);
        emailToken.setCreatedAt(LocalDateTime.now());
        emailToken.setExpiresAt(LocalDateTime.now().plus(ConfirmTokenExpireInMinutes, ChronoUnit.MINUTES));
        Token emailTokenSaved = tokenService.save(emailToken);

        return emailTokenSaved;
    }

    @Override
    public UserInfo getUserInfoByEmail(String email) {
        User user = userRepo.findEnabledUserByEmail(email);
        if (user == null)
            return null;

        return new UserInfo(user);
    }

    @Override
    public void changePassword(ChangePasswordDto dto) {
        // TODO Auto-generated method stub

    }

    @Override
    public void resetPassword(ResetPasswordDto dto) {
        // TODO Auto-generated method stub

    }

    @Override
    public UserInfo getUserInfoById(Long id) {
        User user = userRepo.findEnabledUserById(id);
        if (user == null)
            return null;
        return new UserInfo(user);
    }
}
