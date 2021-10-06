package com.fantasticfour.shareyourrecipes.user;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.ERole;
import com.fantasticfour.shareyourrecipes.domains.Role;
import com.fantasticfour.shareyourrecipes.domains.User;
import com.fantasticfour.shareyourrecipes.user.payload.SignUpRequest;

public interface UserService {
    void signUp(SignUpRequest request);

    void getUser(SignUpRequest request);

    void blockUser(SignUpRequest request);

    void enableUser(SignUpRequest request);

    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, ERole roleName);

    User getUser(String username);

    List<User> getUsers();

    Boolean isExistsUserByEmail(String email);

    void verifyEmailByToken(String token);

    void resetPasswordByToken(String token, String newPassword);

    void activateUser(String username);

    User getUserByEmail(String email);
}
