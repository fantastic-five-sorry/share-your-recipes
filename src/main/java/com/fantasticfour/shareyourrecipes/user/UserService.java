package com.fantasticfour.shareyourrecipes.user;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.Role;
import com.fantasticfour.shareyourrecipes.domains.Token;
import com.fantasticfour.shareyourrecipes.domains.User;
import com.fantasticfour.shareyourrecipes.domains.enums.ERole;
import com.fantasticfour.shareyourrecipes.domains.enums.ETokenPurpose;
import com.fantasticfour.shareyourrecipes.user.dtos.SignUpRequest;

public interface UserService {
    void signUp(SignUpRequest request);

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

    User getUserByEmail(String email);

    Token createToken(User user, String token, ETokenPurpose purpose);
}
