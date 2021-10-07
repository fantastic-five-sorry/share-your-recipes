package com.fantasticfour.shareyourrecipes.user;

import java.util.Optional;

import javax.transaction.Transactional;

import com.fantasticfour.shareyourrecipes.domains.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    
    Optional<User> findByEmail(String email);

    // @Query("SELECT u FROM User u WHERE u.active = true AND u.username =
    // :username")
    // User findByUsername(String username);

    // User findByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM User c WHERE c.email = :email")
    Boolean isExistsUserByEmail(String email);

    // @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM User c
    // WHERE c.username = :username")
    // Boolean isExistsUserByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE User u set u.enable = true WHERE u.email = :email")
    void activateUser(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u set u.password = :newPassword WHERE u.id = :id")
    void resetPassword(Long id, String newPassword);
}
