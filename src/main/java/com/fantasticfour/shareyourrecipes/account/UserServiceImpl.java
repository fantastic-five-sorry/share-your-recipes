package com.fantasticfour.shareyourrecipes.account;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.account.dtos.ChangePasswordDto;
import com.fantasticfour.shareyourrecipes.account.dtos.ResetPasswordDto;
import com.fantasticfour.shareyourrecipes.account.dtos.SignUpDto;
import com.fantasticfour.shareyourrecipes.account.dtos.UserInfo;
import com.fantasticfour.shareyourrecipes.domains.auth.Role;
import com.fantasticfour.shareyourrecipes.domains.auth.Token;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.enums.ERole;
import com.fantasticfour.shareyourrecipes.domains.enums.ETokenPurpose;
import com.fantasticfour.shareyourrecipes.tokens.TokenService;

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
    public User saveUser(User user) {
        return userRepo.saveAndFlush(user);
    }

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
    public void registerNewAccount(SignUpDto request) {
        if (!request.getPassword().equals(request.getConfirmPassword()))
            throw new IllegalStateException("Confirm password not match");
        if (userRepo.findByEmail(request.getEmail()).isPresent())
            throw new IllegalStateException("Email exists");
        User newUser = new User(request.getEmail(), passwordEncoder.encode(request.getPassword()), request.getName());
        newUser.getRoles().add(roleRepo.findByName(ERole.ROLE_USER));
        userRepo.save(newUser);
    }

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

    public List<User> getUsers() {
        return userRepo.findAll();
    };

    public Boolean isExistsUserByEmail(String email) {
        return userRepo.isExistsUserByEmail(email);
    };

    public void verifyEmailByToken(String token) {
        Token confirmationToken = tokenService.getValidToken(token, ETokenPurpose.VERIFY_EMAIL);

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
        Token forgotToken = tokenService.getValidToken(token, ETokenPurpose.RESET_PASSWORD);
        tokenService.updateTokenUsedAt(token, ETokenPurpose.RESET_PASSWORD);
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
        User user = userRepo.findValidUserByEmail(email);
        if (user == null)
            return null;

        return new UserInfo(user);
    }

    @Override
    @Transactional
    public void changePassword(Long uid, ChangePasswordDto dto) {
        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword()))
            throw new IllegalStateException("Password not match");
        // reset pw
        User user = userRepo.findValidUserById(uid);

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword()))
            throw new IllegalStateException("Current password not match");

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepo.save(user);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordDto dto) {
        // pw not match check
        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword()))
            throw new IllegalStateException("Password not match");
        // get token valid
        Token tokenObj = tokenService.getValidToken(dto.getToken(), ETokenPurpose.RESET_PASSWORD);

        // reset pw
        User user = tokenObj.getUser();

        if (user == null)
            throw new IllegalStateException("user not found");
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        //
        userRepo.save(user);

        tokenService.updateTokenUsedAt(tokenObj.getToken(), ETokenPurpose.RESET_PASSWORD);

    }

    @Override
    public UserInfo getUserInfoById(Long id) {
        User user = userRepo.findValidUserById(id);
        if (user == null)
            return null;
        return new UserInfo(user);
    }
}
