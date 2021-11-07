package com.fantasticfour.shareyourrecipes.admin;

import com.fantasticfour.shareyourrecipes.account.UserService;
import com.fantasticfour.shareyourrecipes.account.dtos.UserRoleDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")

public class AdminRestController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    public Page<UserRoleDto> getUsers(Pageable page) {
        return userService.getUsers(page);
    }

    @GetMapping("/recipes")
    public Page<UserRoleDto> getRecipes(Pageable page) {
        return userService.getUsers(page);
    }

    @GetMapping("/reports")
    public Page<UserRoleDto> getReports(Pageable page) {
        return userService.getUsers(page);
    }

    @GetMapping("/questions")
    public Page<UserRoleDto> getQuestions(Pageable page) {
        return userService.getUsers(page);
    }

    @GetMapping("/stats")
    public Page<UserRoleDto> getStats(Pageable page) {
        return userService.getUsers(page);
    }

    // lam yeu user
    @PutMapping("/users/{id}")
    public ResponseEntity<?> degradeUser(@PathVariable("id") Long uid) {
        return null;
    }
}
