package com.fantasticfour.shareyourrecipes.account.dtos;

import com.fantasticfour.shareyourrecipes.domains.auth.User;

public class UserInfo {
    private String email;
    private String name;

    private String photoUrl;

    private Long id;

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

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserInfo() {
    }

    public UserInfo(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.photoUrl = user.getPhotoUrl() != null ? user.getPhotoUrl() : "/imgs/default-avatar.png";
        this.name = user.getName();
    }

}
