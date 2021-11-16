package com.fantasticfour.shareyourrecipes.account.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.fantasticfour.shareyourrecipes.domains.auth.User;

public class UserRoleDto extends UserInfo {
    private List<String> roles;

    private Boolean enabled;
    private Boolean blocked;

    public Boolean isEnabled() {
        return this.enabled;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isBlocked() {
        return this.blocked;
    }

    public Boolean getBlocked() {
        return this.blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public UserRoleDto(User user) {
        super(user);
        this.enabled = user.getEnable();
        this.blocked = user.getBlocked();
        roles = user.getRoles().stream().map(role -> role.getName().toString()).collect(Collectors.toList());
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
