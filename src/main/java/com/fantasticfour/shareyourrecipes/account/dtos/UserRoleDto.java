package com.fantasticfour.shareyourrecipes.account.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.fantasticfour.shareyourrecipes.domains.auth.User;

public class UserRoleDto extends UserInfo {
    private List<String> roles;

    public UserRoleDto(User user) {
        super(user);
        roles = user.getRoles().stream().map(role -> role.toString()).collect(Collectors.toList());
    }

    public UserRoleDto() {
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}
