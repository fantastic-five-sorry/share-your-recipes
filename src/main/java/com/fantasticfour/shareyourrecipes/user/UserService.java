package com.fantasticfour.shareyourrecipes.user;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.auth.Role;
import com.fantasticfour.shareyourrecipes.domains.auth.Token;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.enums.ERole;
import com.fantasticfour.shareyourrecipes.domains.enums.ETokenPurpose;
import com.fantasticfour.shareyourrecipes.user.dtos.ChangePasswordDto;
import com.fantasticfour.shareyourrecipes.user.dtos.ResetPasswordDto;
import com.fantasticfour.shareyourrecipes.user.dtos.SignUpDto;
import com.fantasticfour.shareyourrecipes.user.dtos.UserInfo;

public interface UserService {
    void registerNewAccount(SignUpDto request);

    void blockUser(String email);

    void unblockUser(String email);

    void enableUser(String email);

    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String email, ERole roleName);

    User getEnabledUser(String email);

    List<User> getUsers();

    Boolean isExistsUserByEmail(String email);

    void verifyEmailByToken(String token);

    void resetPasswordByToken(String token, String newPassword);

    void activateUser(String email);

    User getValidUserByEmail(String email);

    User getUserByEmail(String email);

    Token createToken(User user, String token, ETokenPurpose purpose);

    UserInfo getUserInfoByEmail(String email);
    
    UserInfo getUserInfoById(Long id);

    void changePassword(ChangePasswordDto dto);

    void resetPassword(ResetPasswordDto dto);
}
