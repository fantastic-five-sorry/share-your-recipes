package com.fantasticfour.shareyourrecipes.user;

import com.fantasticfour.shareyourrecipes.user.payload.SignUpRequest;

public interface UserService {
    void signUp(SignUpRequest request);

    void getUser(SignUpRequest request);

    void blockUser(SignUpRequest request);

    void enableUser(SignUpRequest request);
}
