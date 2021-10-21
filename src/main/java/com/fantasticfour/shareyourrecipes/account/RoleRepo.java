package com.fantasticfour.shareyourrecipes.account;

import com.fantasticfour.shareyourrecipes.domains.auth.Role;
import com.fantasticfour.shareyourrecipes.domains.enums.ERole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}
