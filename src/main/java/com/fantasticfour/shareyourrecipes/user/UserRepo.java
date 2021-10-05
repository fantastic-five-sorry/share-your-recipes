package com.fantasticfour.shareyourrecipes.user;

import java.util.Optional;

import com.fantasticfour.shareyourrecipes.domains.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
