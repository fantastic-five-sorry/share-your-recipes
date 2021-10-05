package com.fantasticfour.shareyourrecipes.user;

import com.fantasticfour.shareyourrecipes.domains.User;
import com.fantasticfour.shareyourrecipes.user.payload.SignUpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

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

}
