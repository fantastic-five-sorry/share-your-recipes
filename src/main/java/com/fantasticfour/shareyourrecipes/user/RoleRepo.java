package com.fantasticfour.shareyourrecipes.user;

import com.fantasticfour.shareyourrecipes.domains.ERole;
import com.fantasticfour.shareyourrecipes.domains.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}
