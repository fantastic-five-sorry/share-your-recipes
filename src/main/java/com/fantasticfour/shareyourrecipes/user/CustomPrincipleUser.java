package com.fantasticfour.shareyourrecipes.user;

import org.springframework.security.core.userdetails.User;

public class CustomPrincipleUser extends User {

    private String email;
    private String name;

    public CustomPrincipleUser(com.fantasticfour.shareyourrecipes.domains.User user,
            java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> authorities) {
        super(user.getEmail(), null, authorities);
        this.email = user.getEmail();
        this.name = user.getName();

    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
