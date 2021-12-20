package com.fantasticfour.shareyourrecipes.admin;

import com.fantasticfour.shareyourrecipes.account.RoleRepo;
import com.fantasticfour.shareyourrecipes.account.UserRepo;
import com.fantasticfour.shareyourrecipes.account.UserService;
import com.fantasticfour.shareyourrecipes.account.dtos.SignUpDto;
import com.fantasticfour.shareyourrecipes.account.dtos.UserRoleDto;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.enums.ERole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")

public class AdminRestController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/users")
    public Page<UserRoleDto> getUsers(Pageable page) {
        return userService.getUsers(page);
    }

    @PostMapping("/addChef")
    public ResponseEntity<?> addChef(@RequestBody SignUpDto signUpDto) {
        try {
            User user = new User();
            user.setEmail(signUpDto.getEmail());
            user.setName(signUpDto.getName());
            user.setPassword(encoder.encode(signUpDto.getPassword()));
            user.setEnabled(true);
            user.getRoles().add(roleRepo.findByName(ERole.ROLE_CHEF));
            userService.saveUser(user);
            return ResponseEntity.ok().body("Success");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/recipes")
    public Page<UserRoleDto> getRecipes(Pageable page) {
        return userService.getUsers(page);
    }

    @PostMapping("/blockUser/{id}")
    public ResponseEntity<?> blockUser(@PathVariable("id") Long uid) {
        try {
            User user = userRepo.findById(uid).orElseThrow(() -> new IllegalStateException("User not found"));

            user.setBlocked(true);
            userRepo.save(user);

            return ResponseEntity.ok().body("Success");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        // return userService.getUsers(page);
    }

    @PostMapping("/unblockUser/{id}")
    public ResponseEntity<?> unBlockUser(@PathVariable("id") Long uid) {
        try {
            User user = userRepo.findById(uid).orElseThrow(() -> new IllegalStateException("User not found"));

            user.setBlocked(false);
            userRepo.save(user);

            return ResponseEntity.ok().body("Success");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
