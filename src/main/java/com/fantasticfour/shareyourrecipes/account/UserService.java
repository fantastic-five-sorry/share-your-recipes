package com.fantasticfour.shareyourrecipes.account;

import java.util.List;

import com.fantasticfour.shareyourrecipes.account.dtos.ChangePasswordDto;
import com.fantasticfour.shareyourrecipes.account.dtos.ResetPasswordDto;
import com.fantasticfour.shareyourrecipes.account.dtos.SignUpDto;
import com.fantasticfour.shareyourrecipes.account.dtos.UserInfo;
import com.fantasticfour.shareyourrecipes.domains.auth.Role;
import com.fantasticfour.shareyourrecipes.domains.auth.Token;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.enums.ERole;
import com.fantasticfour.shareyourrecipes.domains.enums.ETokenPurpose;

public interface UserService {
    User registerNewAccount(SignUpDto request);

    void blockUser(String email);

    void unblockUser(String email);

    Role saveRole(Role role);

    void addRoleToUser(String email, ERole roleName);

    List<User> getUsers();

    Boolean isExistsUserByEmail(String email);

    void verifyEmailByToken(String token);

    void resetPasswordByToken(String token, String newPassword);

    void activateUser(String email);

    User getValidUserByEmail(String email);

    User getValidUserById(Long email);

    User getUserByEmail(String email);

    Token createToken(User user, String token, ETokenPurpose purpose);

    UserInfo getUserInfoByEmail(String email);

    UserInfo getUserInfoById(Long id);

    void changePassword(Long uid, ChangePasswordDto dto);

    void resetPassword(ResetPasswordDto dto);

    User saveUser(User user);

    void updateAvatar(Long id, String url);

}
